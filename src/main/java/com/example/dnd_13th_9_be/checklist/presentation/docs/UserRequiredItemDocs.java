package com.example.dnd_13th_9_be.checklist.presentation.docs;

import java.util.Map;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.dnd_13th_9_be.checklist.presentation.dto.ReplaceUserRequiredItemRequest;
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

  @Operation(
      summary = "필수 확인 교체",
      description =
          """
            체크리스트 관리 > 필수 확인 목록을 교체합니다.
            기존에 등록된 필수 확인 아이템들을 요청 값에 포함된 itemIdList로 교체합니다.
            - Path: `/api/required-item`
            - Method: `POST`

            예: 기존에 사용자가 [1, 21]을 설정한 상태에서
            요청 값으로 [4, 20, 21]을 보내면 최종적으로 [4, 20, 21]이 필수 확인으로 등록됩니다.
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
        responseCode = "404",
        description = "존재하지 않는 체크리스트 추가를 시도한 경우",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples =
                    @ExampleObject(
                        name = "존재하지 않는 체크리스트",
                        value =
                            """
                  {
                    "code": "73000",
                    "message": "존재하지 않는 체크리스트 입니다",
                    "data": null
                  }
                  """)))
  })
  @PutMapping
  ResponseEntity<ApiResponse<Map<String, Object>>> replace(
      @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipalDto userDetails,
      @Valid @RequestBody ReplaceUserRequiredItemRequest request);
}
