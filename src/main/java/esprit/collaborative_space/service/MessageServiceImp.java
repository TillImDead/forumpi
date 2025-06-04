package esprit.collaborative_space.service;

import esprit.collaborative_space.entity.Message;
import esprit.collaborative_space.repository.IMessageRepository;
import esprit.collaborative_space.repository.IRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MessageServiceImp implements IMessageService{
    @Autowired
    private IMessageRepository messageRepository;


    @Autowired
    private IRoomRepository roomRepository;

    @Override
    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public List<Message> getMessagesByRoomId(Long roomId) {
        return messageRepository.findByRoomId(roomId);
    }

    @Override
    public Message getMessageById(Long id) {
        return messageRepository.findById(id).orElseThrow(() -> new RuntimeException("Message not found"));
    }


    @Override
    public void deleteMessage(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        messageRepository.delete(message);
    }
}
