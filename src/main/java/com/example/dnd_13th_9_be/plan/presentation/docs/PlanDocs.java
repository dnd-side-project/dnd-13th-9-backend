package com.example.dnd_13th_9_be.plan.presentation.docs;

import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.dnd_13th_9_be.global.response.ApiResponse;
import com.example.dnd_13th_9_be.plan.presentation.dto.request.CreatePlanRequest;
import com.example.dnd_13th_9_be.plan.presentation.dto.request.RenamePlanRequest;
import com.example.dnd_13th_9_be.plan.presentation.dto.response.PlanDetailResponse;
import com.example.dnd_13th_9_be.plan.presentation.dto.response.PlanSummaryResponse;
import com.example.dnd_13th_9_be.user.application.dto.UserPrincipalDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "계획", description = "계획(Plan) 관련 API")
public interface PlanDocs {
  @Operation(summary = "계획 목록 조회", description = "로그인한 사용자의 모든 계획 목록을 반환한다.")
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
                        name = "성공 예시",
                        value =
                            """
                  {
                    "code": "20000",
                    "message": "성공했습니다",
                    "data": [
                      {
                        "planId": 4,
                        "name": "새로 생성한 계획",
                        "createdAt": "2025-08-12T21:43:18.429603",
                        "folderCount": 0,
                        "isDefaultPlan": false
                      },
                      {
                        "planId": 1,
                        "name": "기본 계획",
                        "createdAt": "2025-08-11T20:24:59",
                        "folderCount": 1,
                        "isDefaultPlan": true
                      }
                    ]
                  }
                  """))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "404",
        description = "유효하지 않은 사용자",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples =
                    @ExampleObject(
                        name = "유저 없음 예시",
                        value =
                            """
                  {
                    "code": "60401",
                    "message": "사용자를 찾을 수 없습니다.",
                    "data": null
                  }
                  """)))
  })
  @GetMapping
  ResponseEntity<ApiResponse<List<PlanSummaryResponse>>> getPlanList(
      @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipalDto userDetails);

  @Operation(summary = "계획 생성", description = "로그인한 사용자의 새 계획을 생성한다.")
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
                        name = "성공 예시",
                        value =
                            """
                  {
                    "code": "20000",
                    "message": "성공했습니다",
                    "data": {
                      "planId": 5,
                      "name": "새로 생성한 계획2",
                      "createdAt": "2025-08-12T21:47:01.903445",
                      "isDefaultPlan": false
                    }
                  }
                  """))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "400",
        description = "요청 본문이 비어 있음",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples =
                    @ExampleObject(
                        name = "본문 없음 예시",
                        value =
                            """
                  {
                    "code": "40000",
                    "message": "요청이 올바르지 않습니다",
                    "data": {
                      "reason": "요청 본문이 비어 있습니다",
                      "hint": "Content-Type: application/json 으로 JSON 본문을 보내주세요"
                    }
                  }
                  """))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "400",
        description = "계획 최대 생성 가능 갯수 초과",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples =
                    @ExampleObject(
                        name = "최대 갯수 초과 예시",
                        value =
                            """
                  {
                    "code": "71001",
                    "message": "최대 생성할 수 있는 계획 갯수를 초과했습니다",
                    "data": null
                  }
                  """))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "404",
        description = "유효하지 않은 사용자",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples =
                    @ExampleObject(
                        name = "유저 없음 예시",
                        value =
                            """
                  {
                    "code": "60401",
                    "message": "사용자를 찾을 수 없습니다.",
                    "data": null
                  }
                  """))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "422",
        description = "유효성 검증 실패",
        content =
            @Content(
                mediaType = "application/json",
                schema =
                    @Schema(
                        implementation = io.swagger.v3.oas.annotations.responses.ApiResponse.class),
                examples =
                    @ExampleObject(
                        name = "검증 실패 예시",
                        value =
                            """
                  {
                    "code": "42200",
                    "message": "검증 오류가 발생했습니다",
                    "data": {
                      "name": "크기가 1에서 10 사이여야 합니다"
                    }
                  }
                  """)))
  })
  @PostMapping
  ResponseEntity<ApiResponse<PlanDetailResponse>> create(
      @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipalDto userDetails,
      @Valid @RequestBody CreatePlanRequest request);

  @Operation(summary = "계획 이름 변경", description = "지정된 계획의 이름을 변경한다.")
  @ApiResponses({
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "성공(빈 데이터)",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples =
                    @ExampleObject(
                        name = "성공 예시",
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
        description = "유효하지 않은 계획",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples =
                    @ExampleObject(
                        name = "계획 없음 예시",
                        value =
                            """
                  {
                    "code": "71000",
                    "message": "유효하지 않은 계획입니다",
                    "data": null
                  }
                  """))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "422",
        description = "유효성 검증 실패",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples =
                    @ExampleObject(
                        name = "검증 실패 예시",
                        value =
                            """
                  {
                    "code": "42200",
                    "message": "검증 오류가 발생했습니다",
                    "data": {
                      "name": "크기가 1에서 10 사이여야 합니다"
                    }
                  }
                  """)))
  })
  @PatchMapping("/{planId}")
  ResponseEntity<ApiResponse<Map<String, Object>>> rename(
      @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipalDto userDetails,
      @Parameter(description = "계획 고유 인덱스 값", example = "5", required = true)
          @PathVariable("planId")
          Long planId,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              required = true,
              description = "계획 이름 변경 요청",
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = RenamePlanRequest.class),
                      examples = @ExampleObject(value = "{ \"name\": \"새 이름\" }")))
          @Valid
          @RequestBody
          RenamePlanRequest request);

  @Operation(summary = "계획 삭제", description = "지정된 계획을 삭제한다. 기본 계획은 삭제할 수 없다.")
  @ApiResponses({
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "성공(빈 데이터)",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples =
                    @ExampleObject(
                        name = "성공 예시",
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
        description = "유효하지 않은 계획",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples =
                    @ExampleObject(
                        name = "계획 없음 예시",
                        value =
                            """
                  {
                    "code": "71000",
                    "message": "유효하지 않은 계획입니다",
                    "data": null
                  }
                  """))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "403",
        description = "기본 계획 삭제 금지",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples =
                    @ExampleObject(
                        name = "기본 계획 삭제 실패 예시",
                        value =
                            """
                  {
                    "code": "71004",
                    "message": "기본 계획은 삭제 할 수 없습니다",
                    "data": null
                  }
                  """)))
  })
  @DeleteMapping("/{planId}")
  ResponseEntity<ApiResponse<Map<String, Object>>> delete(
      @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipalDto userDetails,
      @Parameter(description = "계획 고유 인덱스 값", example = "5", required = true)
          @PathVariable("planId")
          Long planId);
}
