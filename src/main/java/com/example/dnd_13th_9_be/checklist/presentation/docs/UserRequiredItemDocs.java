package com.example.dnd_13th_9_be.checklist.presentation.docs;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.dnd_13th_9_be.global.response.ApiResponse;
import com.example.dnd_13th_9_be.user.application.dto.UserPrincipalDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "필수 확인 관리", description = "체크리스트 아이템을 사용자별 '필수 확인' 목록에 추가/삭제 합니다.")
public interface UserRequiredItemDocs {
  @Operation(
      summary = "필수 확인 추가",
      description =
          """
          체크리스트 관리 > 필수 확인 목록에 아이템을 추가합니다.
          - Path: `/api/required-item/{itemId}`
          - Method: `PUT`
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
                        name = "성공 응답",
                        value =
                            """
                      {
                        "code": "20000",
                        "message": "성공했습니다",
                        "data": {}
                      }
                      """))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "409",
        description = "이미 추가된 체크리스트",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples =
                    @ExampleObject(
                        name = "이미 추가됨",
                        value =
                            """
                      {
                        "code": "73001",
                        "message": "이미 필수 확인에 추가된 체크리스트 입니다",
                        "data": null
                      }
                      """))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "404",
        description = "존재하지 않는 체크리스트",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples =
                    @ExampleObject(
                        name = "아이템 없음",
                        value =
                            """
                      {
                        "code": "73000",
                        "message": "존재하지 않는 체크리스트 입니다",
                        "data": null
                      }
                      """)))
  })
  ResponseEntity<ApiResponse<Map<String, Object>>> add(
      @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipalDto userDetails,
      @Parameter(name = "itemId", description = "체크리스트 아이템 고유 인덱스", required = true, example = "12")
          @PathVariable("itemId")
          Long itemId);

  @Operation(
      summary = "필수 확인 삭제",
      description =
          """
          체크리스트 관리 > 필수 확인 목록에서 아이템을 삭제합니다.
          - Path: `/api/required-item/{itemId}`
          - Method: `DELETE`
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
                        name = "성공 응답",
                        value =
                            """
                      {
                        "code": "20000",
                        "message": "성공했습니다",
                        "data": {}
                      }
                      """))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "409",
        description = "이미 삭제되었거나 존재하지 않음",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples =
                    @ExampleObject(
                        name = "이미 삭제됨",
                        value =
                            """
                      {
                        "code": "73002",
                        "message": "이미 필수 확인에서 삭제된 체크리스트 입니다",
                        "data": null
                      }
                      """))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "404",
        description = "존재하지 않는 체크리스트",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples =
                    @ExampleObject(
                        name = "아이템 없음",
                        value =
                            """
                      {
                        "code": "73000",
                        "message": "존재하지 않는 체크리스트 입니다",
                        "data": null
                      }
                      """)))
  })
  ResponseEntity<ApiResponse<Map<String, Object>>> delete(
      @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipalDto userDetails,
      @Parameter(
              name = "itemId",
              description = "체크리스트 아이템 고유 인덱스",
              required = true,
              example = "123")
          @PathVariable("itemId")
          Long itemId);
}
