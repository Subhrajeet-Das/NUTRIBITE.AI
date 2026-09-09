package com.nutribite.ai.snap.controller;
import com.nutribite.ai.snap.dto.SnapErrorResponse; import org.springframework.beans.factory.annotation.Value; import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import org.springframework.web.multipart.MultipartFile;
@RestController @RequestMapping("/api/snap") public class SnapController {
 @Value("${snap.enabled:false}") private boolean enabled;
 @PostMapping(value="/analyze",consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
 public ResponseEntity<SnapErrorResponse> analyze(@RequestPart("image") MultipartFile image){
  if(image==null||image.isEmpty()) return ResponseEntity.badRequest().body(SnapErrorResponse.builder().code("IMAGE_REQUIRED").message("Please upload a food image.").build());
  if(!enabled) return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(SnapErrorResponse.builder().code("SNAP_AI_NOT_CONFIGURED").message("Snap AI is not configured yet. Connect the image-analysis service before requesting recognition.").build());
  return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(SnapErrorResponse.builder().code("SNAP_AI_ADAPTER_MISSING").message("Snap AI is enabled but no provider adapter is configured.").build());
 }
}
