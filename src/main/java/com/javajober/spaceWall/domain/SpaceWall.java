package com.javajober.spaceWall.domain;

import com.javajober.space.domain.AddSpace;
import com.javajober.member.domain.Member;
import com.javajober.spaceWall.dto.request.DataStringUpdateRequest;
import com.javajober.spaceWall.filedto.DataUpdateRequest;
import com.javajober.spaceWall.spaceWallCategory.domain.SpaceWallCategoryType;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@DynamicInsert
@Getter
@Table(name = "space_wall")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class SpaceWall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "blocks", columnDefinition = "text", nullable = false)
    private String blocks;

    @Column(name ="share_url", nullable = false)
    private String shareURL;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "add_space_id", nullable = false)
    private AddSpace addSpace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(name = "space_wall_category", nullable = false)
    private SpaceWallCategoryType spaceWallCategoryType;

    @Enumerated(EnumType.STRING)
    @Column(name = "flag", nullable = false)
    private FlagType flag;

    @ColumnDefault("false")
    @Column(name = "is_public")
    private Boolean isPublic;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    protected SpaceWall() {

    }

    @Builder
    public SpaceWall(final String blocks, final String shareURL, final AddSpace addSpace, final Member member,
                     final SpaceWallCategoryType spaceWallCategoryType, final FlagType flag, final Boolean isPublic) {
        this.blocks = blocks;
        this.shareURL = shareURL;
        this.addSpace = addSpace;
        this.member = member;
        this.spaceWallCategoryType = spaceWallCategoryType;
        this.flag = flag;
        this.isPublic = isPublic;
    }

    public void update(final DataStringUpdateRequest request, final FlagType flag, final String blockInfoArrayAsString){
        this.blocks = blockInfoArrayAsString;
        this.shareURL = request.getShareURL();
        this.flag = flag;
    }

    public void fileUpdate(final DataUpdateRequest request, final FlagType flag, final String blockInfoArrayAsString){
        this.blocks = blockInfoArrayAsString;
        this.shareURL = request.getShareURL();
        this.flag = flag;
    }

    public void updateIsPublic(final Boolean isPublic) {
        this.isPublic = isPublic;
    }
}