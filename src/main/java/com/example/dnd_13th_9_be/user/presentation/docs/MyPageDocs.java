package com.example.dnd_13th_9_be.user.presentation.docs;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.dnd_13th_9_be.global.response.ApiResponse;
import com.example.dnd_13th_9_be.user.application.dto.MyPageDto;
import com.example.dnd_13th_9_be.user.application.dto.UserPrincipalDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "마이 페이지", description = "마이 페이지(MyPage) 관련 API")
public interface MyPageDocs {

  @Operation(
      summary = "내 정보 조회",
      description =
          """
    카카오 서비스 인증을 거치지 않아 현재 이메일과 프로필 이미지는 제공되지 않습니다.
    카카오에서 제공하는 **프로필 이름**을 `name`으로 반환합니다.
    """)
  @ApiResponses({
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "성공",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples =
                    @ExampleObject(
                        name = "성공 응답 예시",
                        value =
                            """
                        {
                          "code": "20000",
                          "message": "성공했습니다",
                          "data": {
                            "name": "카카오프로필이름",
                            "propertyCount": 12,
                            "checklistCount": 3
                          }
                        }
                        """)))
  })
  @GetMapping
  ResponseEntity<ApiResponse<MyPageDto>> getUserInfo(
      @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipalDto userDetails);
}
