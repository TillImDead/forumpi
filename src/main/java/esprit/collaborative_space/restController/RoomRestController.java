package esprit.collaborative_space.restController;

import esprit.collaborative_space.entity.Room;
import esprit.collaborative_space.service.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomRestController {

    @Autowired
    private IRoomService roomService;

    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @PostMapping
    public ResponseEntity<Room> saveRoom(@RequestBody Room room) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.saveRoom(room));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom( @RequestBody Room updatedRoom) {
        return ResponseEntity.ok(roomService.updateRoom(updatedRoom));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}
