package com.javajober.freeBlock.repository;

import com.javajober.core.error.exception.Exception404;
import com.javajober.core.message.ErrorMessage;
import com.javajober.freeBlock.domain.FreeBlock;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface FreeBlockRepository extends Repository<FreeBlock, Long> {

    void save(FreeBlock freeBlock);

    Optional<FreeBlock> findByIdAndDeletedAtIsNull(Long freeId);

    default FreeBlock getById(final Long freeId) {
        return findByIdAndDeletedAtIsNull(freeId)
                .orElseThrow(() -> new Exception404(ErrorMessage.FREE_BLOCK_NOT_FOUND));
    }
}
