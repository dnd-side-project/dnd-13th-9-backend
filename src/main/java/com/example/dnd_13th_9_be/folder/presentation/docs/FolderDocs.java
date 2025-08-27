package com.example.dnd_13th_9_be.folder.presentation.docs;

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

import com.example.dnd_13th_9_be.folder.presentation.dto.request.CreateFolderRequest;
import com.example.dnd_13th_9_be.folder.presentation.dto.request.RenameFolderRequest;
import com.example.dnd_13th_9_be.folder.presentation.dto.response.FolderDetailResponse;
import com.example.dnd_13th_9_be.folder.presentation.dto.response.FolderSummaryResponse;
import com.example.dnd_13th_9_be.folder.presentation.dto.response.QueryFolderMemoListResponse;
import com.example.dnd_13th_9_be.folder.presentation.dto.response.RecordSummaryResponse;
import com.example.dnd_13th_9_be.global.response.ApiResponse;
import com.example.dnd_13th_9_be.user.application.dto.UserPrincipalDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "폴더", description = "폴더(Folder) 관련 API")
public interface FolderDocs {

  @Operation(summary = "폴더 목록 조회", description = "지정된 계획(planId)에 속한 폴더들의 목록을 반환한다.")
  @ApiResponses({
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "정상",
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
                        "folderId": 13,
                        "name": "테스트 폴더 1",
                        "createdAt": "2025-08-12T21:50:44.801487",
                        "recordCount": 0,
                        "isDefaultFolder": false
                      },
                      {
                        "folderId": 1,
                        "name": "기본 폴더",
                        "createdAt": "2025-08-11T20:26:06",
                        "recordCount": 0,
                        "isDefaultFolder": true
                      }
                    ]
                  }
                  """))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "404",
        description = "유효하지 않은 계획 id",
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
                    "code": "40402",
                    "message": "유효하지 않은 계획입니다",
                    "data": null
                  }
                  """)))
  })
  @GetMapping("/{planId}")
  ResponseEntity<ApiResponse<List<FolderSummaryResponse>>> getFolderList(
      @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipalDto userDetails,
      @Parameter(description = "계획 고유 값", example = "1", required = true) @PathVariable("planId")
          Long planId);

  @Operation(summary = "폴더 생성", description = "지정된 계획(planId) 하위에 새 폴더를 생성한다.")
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
                      "folderId": 2,
                      "name": "테스트폴더테스트폴더",
                      "createdAt": "2025-08-12T16:25:14.955725",
                      "isDefaultFolder": false
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
        description = "폴더 최대 생성 가능 갯수 초과",
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
                    "code": "72001",
                    "message": "최대 생성할 수 있는 폴더 갯수를 초과했습니다",
                    "data": null
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
  @PostMapping
  ResponseEntity<ApiResponse<FolderDetailResponse>> create(
      @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipalDto userDetails,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              required = true,
              description = "폴더 생성 요청",
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = CreateFolderRequest.class),
                      examples = @ExampleObject(value = "{ \"planId\": 1, \"name\": \"테스트폴더\" }")))
          @Valid
          @RequestBody
          CreateFolderRequest request);

  @Operation(summary = "폴더 이름 변경", description = "지정된 폴더의 이름을 변경한다.")
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
        description = "유효하지 않은 폴더 id",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples =
                    @ExampleObject(
                        name = "폴더 없음 예시",
                        value =
                            """
                  {
                    "code": "72000",
                    "message": "유효하지 않은 폴더입니다",
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
  @PatchMapping("/{folderId}")
  ResponseEntity<ApiResponse<Map<String, Object>>> rename(
      @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipalDto userDetails,
      @Parameter(description = "폴더 고유 인덱스 값", example = "12", required = true)
          @PathVariable("folderId")
          Long folderId,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              required = true,
              description = "폴더 이름 변경 요청",
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = RenameFolderRequest.class),
                      examples = @ExampleObject(value = "{ \"name\": \"새 폴더 이름\" }")))
          @Valid
          @RequestBody
          RenameFolderRequest request);

  @Operation(summary = "폴더 삭제", description = "지정된 폴더를 삭제한다. 기본 폴더는 삭제할 수 없다.")
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
        description = "유효하지 않은 폴더 id",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples =
                    @ExampleObject(
                        name = "폴더 없음 예시",
                        value =
                            """
                  {
                    "code": "72000",
                    "message": "유효하지 않은 폴더입니다",
                    "data": null
                  }
                  """))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "403",
        description = "기본 폴더 삭제 금지",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples =
                    @ExampleObject(
                        name = "기본 폴더 삭제 실패 예시",
                        value =
                            """
                  {
                    "code": "72004",
                    "message": "기본 폴더는 삭제 할 수 없습니다",
                    "data": null
                  }
                  """)))
  })
  @DeleteMapping("/{folderId}")
  ResponseEntity<ApiResponse<Map<String, Object>>> delete(
      @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipalDto userDetails,
      @Parameter(description = "폴더 고유 인덱스 값", example = "12", required = true)
          @PathVariable("folderId")
          Long folderId);

  @Operation(summary = "폴더 내 기록 조회", description = "지정된 폴더에 포함된 매물/장소 메모 기록 목록을 조회한다.")
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
                    "id": 35,
                    "imageUrl": "https://zipzip-bucket.s3.amazonaws.com/images/58be7686-3fcb-4655-b3aa-fe9e713c0ab7.jpeg",
                    "recordType": "PROPERTY",
                    "feeling": "BAD",
                    "title": "안 괜찮은 원룸",
                    "contractType": "JEONSE",
                    "depositBig": 1,
                    "depositSmall": 2,
                    "managementFee": null,
                    "memo": "이 집은 될 수 있으면 피할 것!",
                    "locationTag": null,
                    "latitude": 35.098237529977,
                    "longitude": 128.981411608042,
                    "createdAt": "2025-08-25T00:11:17.844533"
                  },
                  {
                    "id": 34,
                    "imageUrl": null,
                    "recordType": "PROPERTY",
                    "feeling": "GOOD",
                    "title": "괜찮은 원룸",
                    "contractType": "MONTHLY_RENT",
                    "depositBig": 7,
                    "depositSmall": 10,
                    "managementFee": 10,
                    "memo": "제일 우선 순위",
                    "locationTag": null,
                    "latitude": 35.098237529973,
                    "longitude": 128.981411608041,
                    "createdAt": "2025-08-24T23:51:03.065649"
                  }
                ]
              }
              """))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "404",
        description = "유효하지 않은 폴더 id",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples =
                    @ExampleObject(
                        name = "폴더 없음 예시",
                        value =
                            """
              {
                "code": "72000",
                "message": "유효하지 않은 폴더입니다",
                "data": null
              }
              """)))
  })
  @GetMapping("/{folderId}/records")
  ResponseEntity<ApiResponse<List<RecordSummaryResponse>>> getRecordList(
      @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipalDto userDetails,
      @Parameter(description = "폴더 고유 인덱스 값", example = "12", required = true)
          @PathVariable("folderId")
          Long folderId);

    @Operation(
            summary = "폴더별 메모 리스트 조회",
            description = "특정 폴더의 메물(부동산) 메모와 주변 장소 메모를 함께 조회합니다."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "폴더 메모 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    name = "성공 응답 예시",
                                    value = """
               {
                 "isSuccess": true,
                 "code": "200",
                 "message": "요청이 성공했습니다.",
                 "result": {
                   "recordSummaryResponses": [
                     {
                       "id": 1,
                       "imageUrls": ["https://example.com/image1.jpg", "https://example.com/image2.jpg"],
                       "recordType": "PROPERTY",
                       "feeling": "GOOD",
                       "title": "강남 오피스텔",
                       "contractType": "RENT",
                       "depositBig": 5000,
                       "depositSmall": 0,
                       "managementFee": 150,
                       "memo": "교통이 편리하고 주변 편의시설이 좋음",
                       "locationTag": "강남구",
                       "latitude": 37.4979,
                       "longitude": 127.0276,
                       "createdAt": "2024-08-27T10:30:00"
                     }
                   ],
                   "queryPlaceMemoResponses": [
                     {
                       "id": 1,
                       "title": "강남 카페",
                       "placeTag": "CONVENIENCE",
                       "description": "조용하고 분위기 좋은 카페",
                       "address": "서울 강남구 테헤란로 123",
                       "latitude": "37.12345",
                       "longitude": "127.12345",
                       "folderId": 1,
                       "imageUrls": ["https://example.com/cafe1.jpg", "https://example.com/cafe2.jpg"]
                     }
                   ]
                 }
               }
               """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "폴더를 찾을 수 없음",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
               {
                 "isSuccess": false,
                 "code": "404",
                 "message": "폴더를 찾을 수 없습니다."
               }
               """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "403",
                    description = "폴더 접근 권한 없음",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
               {
                 "isSuccess": false,
                 "code": "403",
                 "message": "해당 폴더에 접근할 권한이 없습니다."
               }
               """
                            )
                    )
            )
    })
    @Parameter(
            name = "folderId",
            description = "조회할 폴더의 ID",
            required = true,
            example = "1",
            schema = @Schema(type = "integer", format = "int64")
    )
    @GetMapping("/{folderId}/memos")
    ResponseEntity<ApiResponse<QueryFolderMemoListResponse>> findAll(
            @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipalDto userPrincipalDto,
            @PathVariable("folderId") Long folderId);


}
