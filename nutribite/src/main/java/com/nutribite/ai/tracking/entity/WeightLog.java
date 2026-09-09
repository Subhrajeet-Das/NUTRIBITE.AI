package com.nutribite.ai.tracking.entity;
import com.nutribite.ai.model.User; import jakarta.persistence.*; import lombok.*; import java.time.LocalDate;
@Entity @Table(name="weight_logs",indexes={@Index(name="idx_weight_user_date",columnList="user_id,logged_on")}) @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class WeightLog { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="user_id",nullable=false) private User user; @Column(name="weight_kg",nullable=false) private Double weightKg; @Column(name="logged_on",nullable=false) private LocalDate loggedOn; @Column(length=250) private String note; @PrePersist void create(){if(loggedOn==null)loggedOn=LocalDate.now();}}
