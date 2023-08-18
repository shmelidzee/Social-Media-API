package com.social.repository;

import com.social.domain.entities.Note;
import com.social.dto.projection.NoteProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {


    @Query(value = "SELECT n.id,\n" +
            "       n.title,\n" +
            "       n.text,\n" +
            "       n.images,\n" +
            "       n.user_id    as authorId,\n" +
            "       u.username   as username,\n" +
            "       n.created_at as createdAt\n" +
            "FROM notes n\n" +
            "         LEFT JOIN users u on u.id = n.user_id\n" +
            "WHERE ((:sortByDate = true AND n.created_at IS NOT NULL)\n" +
            "   OR (:sortByDate = false))\n" +
            "AND n.user_id in (select f.to_user from followers f where f.from_user = :userId)" +
            "ORDER BY n.created_at ASC\n" +
            "limit :lim offset :off", nativeQuery = true)
    List<NoteProjection> getNotes(Long userId, boolean sortByDate, @Param("lim") Integer limit, @Param("off") Integer offset);
}