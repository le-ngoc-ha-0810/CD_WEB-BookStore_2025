package vn.edu.hcmuaf.st.DACN_BookStore_2025.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
public class CategoryEntity {
    @Id
    private int categoryID;
    @Column(name = "code", length = 50)
    private String code;
    @Column(name = "name", length = 50)
    private String name;
    @Column(name = "created_at")
    private LocalDate createdAt;
    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    private List<BookEntity> books = new ArrayList<>();
}
