package esprit.collaborative_space.restController;

import esprit.collaborative_space.entity.Message;
import esprit.collaborative_space.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageRestController {
    @Autowired
    private IMessageService messageService;

    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message createdMessage = messageService.createMessage(message);
        return new ResponseEntity<>(createdMessage, HttpStatus.CREATED);
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<Message>> getMessagesByRoomId(@PathVariable Long roomId) {
        List<Message> messages = messageService.getMessagesByRoomId(roomId);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        Message message = messageService.getMessageById(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
