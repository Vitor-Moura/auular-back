package teste.aular.domain.contract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import teste.aular.domain.entity.Hotel;

import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {

//     @Modifying(clearAutomatically = true)
//     @Query("UPDATE hotel h SET h.phone_number = :phone WHERE h.hotel_id = :id")
//     int updatePhone(@Param("hotel_id") Integer id, @Param("phone_number") String phone);

     boolean existsByDocumentId(String hotelDocumentId);

     boolean existsByEmail(String email);

     boolean existsByPhoneNumber(String phoneNumber);

     @Query("SELECT new teste.aular.domain.entity.Hotel(h.hotelId, h.hotelUuid, h.name, h.email, h.documentId, h.fidelity, " +
             "h.rates, h.phoneNumber, h.description, h.createdAt, h.updatedAt, h.deactivatedAt, h.active, h.isAuthenticated ) " +
             "FROM Hotel h " +
             "WHERE h.hotelUuid = ?1 ")
     Optional<Hotel> getSimpleByHotelUuid(String hotelUuid);
}
