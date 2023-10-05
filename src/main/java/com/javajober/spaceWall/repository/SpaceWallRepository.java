package com.javajober.spaceWall.repository;

import com.javajober.spaceWall.domain.FlagType;
import com.javajober.spaceWall.domain.SpaceWall;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import com.javajober.core.error.exception.Exception404;
import com.javajober.core.message.ErrorMessage;

import java.util.List;
import java.util.Optional;

public interface SpaceWallRepository extends Repository<SpaceWall, Long> {

    boolean existsByShareURL(final String shareURL);

    @Query("SELECT s FROM SpaceWall s LEFT JOIN s.addSpace a LEFT JOIN s.member m " +
            "WHERE a.id = :addSpaceId AND m.id = :memberId")
    List<SpaceWall> findSpaceWalls(@Param("memberId") final Long memberId, @Param("addSpaceId") final Long addSpaceId);

    boolean existsByAddSpaceId(final Long addSpaceId);

    SpaceWall save(final SpaceWall spaceWall);

    List<SpaceWall> findByAddSpaceId(final Long addSpaceId);

    List<SpaceWall> saveAll(final Iterable<SpaceWall> entities);

    Optional<SpaceWall> findByMemberIdAndAddSpaceIdAndFlag(final Long memberId, final Long addSpaceId, final FlagType flag);

    Optional<SpaceWall> findByIdAndAddSpaceIdAndMemberIdAndFlag(final Long id, final Long addSpaceId, final Long memberId, final FlagType flag);

    Optional<SpaceWall> findByShareURL(final String shareURL);

    default List<SpaceWall> findSpaceWallsOrThrow(final Long memberId, final Long addSpaceId) {
        List<SpaceWall> spaceWalls = findSpaceWalls(memberId, addSpaceId);
        if (spaceWalls.isEmpty()) {
            throw new Exception404(ErrorMessage.SPACE_WALL_TEMPORARY_NOT_FOUND);
        }
        return spaceWalls;
    }

    default SpaceWall getById(final Long memberId, final Long spaceWallId) {
        return findSpaceWalls(memberId, spaceWallId).stream()
                .findFirst()
                .orElseThrow(() -> new Exception404(ErrorMessage.ADD_SPACE_NOT_FOUND));
    }

    default SpaceWall findSpaceWall(Long id, Long addSpaceId, Long memberId, FlagType flag) {
        return findByIdAndAddSpaceIdAndMemberIdAndFlag(id, addSpaceId, memberId, flag)
                .orElseThrow(() -> new Exception404(ErrorMessage.SPACE_WALL_NOT_FOUND));
    }

    default SpaceWall getByShareURL(final String shareURL) {
        return findByShareURL(shareURL)
                .orElseThrow(() -> new Exception404(ErrorMessage.SHARE_URL_NOT_FOUND));
    }
}