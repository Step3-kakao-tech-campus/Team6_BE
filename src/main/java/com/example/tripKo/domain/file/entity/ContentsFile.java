package com.example.tripKo.domain.file.entity;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.example.tripKo.domain.contents.entity.Contents;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@DynamicInsert
@DynamicUpdate
@Entity
@NoArgsConstructor(access = PROTECTED)
@Table(name = "contents_has_file")
public class ContentsFile {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "contents_id", nullable = false)
    private Contents contents;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "file_id", nullable = false)
    private File file;

    @Builder
    private ContentsFile(Contents contents, File file) {
        this.contents = contents;
        this.file = file;
    }

}
