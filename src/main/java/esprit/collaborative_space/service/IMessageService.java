package esprit.collaborative_space.service;

import esprit.collaborative_space.entity.Message;

import java.util.List;

public interface IMessageService {
    Message createMessage(Message message);
    List<Message> getMessagesByRoomId(Long roomId);
    Message getMessageById(Long id);
    void deleteMessage(Long id);
}
