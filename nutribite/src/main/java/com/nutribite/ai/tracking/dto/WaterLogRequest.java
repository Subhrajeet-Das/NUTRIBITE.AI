package com.nutribite.ai.tracking.dto; import jakarta.validation.constraints.DecimalMin; import jakarta.validation.constraints.NotNull; import lombok.*; import java.time.LocalDateTime;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder public class WaterLogRequest { @NotNull @DecimalMin("0.1") private Double amountLiters; private LocalDateTime loggedAt; private String note; }
