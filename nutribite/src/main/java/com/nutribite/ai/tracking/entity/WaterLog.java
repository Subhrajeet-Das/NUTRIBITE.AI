package com.nutribite.ai.tracking.entity;
import com.nutribite.ai.model.User; import jakarta.persistence.*; import lombok.*; import java.time.LocalDateTime;
@Entity @Table(name="water_logs",indexes={@Index(name="idx_water_user_time",columnList="user_id,logged_at")}) @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class WaterLog { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="user_id",nullable=false) private User user; @Column(name="amount_liters",nullable=false) private Double amountLiters; @Column(name="logged_at",nullable=false) private LocalDateTime loggedAt; @Column(length=250) private String note; @PrePersist void create(){if(loggedAt==null)loggedAt=LocalDateTime.now();}}
