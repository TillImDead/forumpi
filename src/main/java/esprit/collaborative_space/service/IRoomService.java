package esprit.collaborative_space.service;

import esprit.collaborative_space.entity.Room;

import java.util.List;

public interface IRoomService {
    List<Room> getAllRooms();
    Room getRoomById(Long id);
    Room saveRoom(Room room);

    Room updateRoom(Room updatedRoom);

    void deleteRoom(Long id);
}
