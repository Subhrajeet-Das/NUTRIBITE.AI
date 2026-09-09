package com.nutribite.ai.tracking.dto; import jakarta.validation.constraints.DecimalMin; import jakarta.validation.constraints.NotNull; import lombok.*; import java.time.LocalDate;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder public class WeightLogRequest { @NotNull @DecimalMin("20.0") private Double weightKg; private LocalDate loggedOn; private String note; }
