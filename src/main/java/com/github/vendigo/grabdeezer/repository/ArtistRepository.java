package com.github.vendigo.grabdeezer.repository;

import com.github.vendigo.grabdeezer.entity.ArtistEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Set;

public interface ArtistRepository extends JpaRepository<ArtistEntity, Long> {

    @Query("SELECT artist.id FROM ArtistEntity artist WHERE artist.id in :artistIds")
    Set<Long> findPresentIds(@Param("artistIds") Collection<Long> artistIds);

    @Query("""
            SELECT artist FROM ArtistEntity artist WHERE
             artist.fullLoaded = false AND artist.topLoaded = false
             ORDER BY artist.priority DESC, artist.createdDate, artist.fans DESC NULLS LAST
            """)
    Page<ArtistEntity> findArtistIdsToTopLoad(Pageable pageable);

    @Query("SELECT count(artist) FROM ArtistEntity artist WHERE artist.fullLoaded = false AND artist.topLoaded = false")
    Long countArtistIdsToTopLoad();
}
