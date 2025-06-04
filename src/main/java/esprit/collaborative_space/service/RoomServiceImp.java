package esprit.collaborative_space.service;

import esprit.collaborative_space.entity.Room;
import esprit.collaborative_space.repository.IRoomRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoomServiceImp implements IRoomService{
    @Autowired
    private IRoomRepository roomRepository;
    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room not found with id: " + id));
    }

    @Override
    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Room updateRoom(Room updatedRoom) {

        return roomRepository.save(updatedRoom);
    }

    @Override
    public void deleteRoom(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new EntityNotFoundException("Room not found with id: " + id);
        }
        roomRepository.deleteById(id);
    }
}
