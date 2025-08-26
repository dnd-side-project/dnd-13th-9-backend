package com.example.dnd_13th_9_be.property.presentation.docs;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.example.dnd_13th_9_be.global.response.ApiResponse;
import com.example.dnd_13th_9_be.property.application.model.RecentPropertyModel;
import com.example.dnd_13th_9_be.property.presentation.dto.request.UpsertPropertyRequest;
import com.example.dnd_13th_9_be.property.presentation.dto.response.PropertyDetailResponse;
import com.example.dnd_13th_9_be.user.application.dto.UserPrincipalDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "매물", description = "매물(Property) 관련 API")
public interface PropertyDocs {

  @Operation(
      summary = "매물 생성",
      description =
          "JSON은 `data` 파트(`application/json`), 이미지는 `image` 파트(`multipart/form-data`, 여러 장 가능)로 업로드한다.")
  @io.swagger.v3.oas.annotations.parameters.RequestBody(
      required = true,
      content =
          @Content(
              mediaType = "multipart/form-data",
              schema = @Schema(implementation = UpsertPropertyMultipartDocs.class),
              examples =
                  @ExampleObject(
                      name = "폼데이터 예시",
                      value =
                          """
                  {
                    "data": {
                      "feeling":"GOOD",
                      "propertyName":"괜찮은 원룸",
                      "memo":"제일 우선 순위",
                      "referenceUrl":"https://naver.me/Gub7uDq5",
                      "address":"35 제주특별자치도 서귀포시 안덕면 중산간서로1615번길 251-24",
                      "detailAddress":"105층",
                      "longitude":"128.981411608041",
                      "latitude":"35.098237529973",
                      "contractType":"MONTHLY_RENT",
                      "houseType":"ETC",
                      "depositBig":7,
                      "depositSmall":10,
                      "managementFee":10,
                      "moveInInfo":"9월초",
                      "categoryMemoList":[
                        {"categoryId":0,"memo":"필수 확인 메모 입니다!!"},
                        {"categoryId":1,"memo":"메인 공간 메모입니다"},
                        {"categoryId":3,"memo":"욕실 공간 메모입니다"}
                      ],
                      "folderId":1
                    },
                    "image": "<<여러 개의 파일 업로드 가능: 같은 키 'image'로 반복 첨부>>"
                  }
                  """)))
  @io.swagger.v3.oas.annotations.responses.ApiResponses({
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "201",
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
                        "data": {}
                      }
                      """))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "400",
        description = "요청 값 검증 실패",
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
                        "code": "40000",
                        "message": "요청 값이 유효하지 않습니다.",
                        "data": null
                      }
                      """))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "401",
        description = "인증 실패",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "403",
        description = "권한 없음",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "500",
        description = "서버 오류",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class)))
  })
  @PostMapping
  ResponseEntity<ApiResponse<Map<String, Object>>> create(
      @Parameter(hidden = true) UserPrincipalDto userDetails,
      @Parameter(
              description = "이미지 파일들 (여러 장 가능, 키는 `image`로 고정)",
              content =
                  @Content(
                      array = @ArraySchema(schema = @Schema(type = "string", format = "binary"))))
          @RequestPart(value = "image", required = false)
          List<MultipartFile> files,
      @Parameter(
              description = "요청 본문(JSON). 표의 제약 조건을 따름",
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = UpsertPropertyRequest.class)))
          @Validated
          @RequestPart(value = "data")
          UpsertPropertyRequest request);

  @Operation(summary = "매물 삭제", description = "경로 변수로 전달된 `propertyId` 에 해당하는 매물을 삭제한다.")
  @io.swagger.v3.oas.annotations.responses.ApiResponses({
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
                        "data": {}
                      }
                      """))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "404",
        description = "존재하지 않거나 권한이 없는 매물",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples =
                    @ExampleObject(
                        name = "Not Found 예시",
                        value =
                            """
                      {
                        "code": "74001",
                        "message": "존재하지 않는 매물입니다",
                        "data": null
                      }
                      """)))
  })
  @DeleteMapping("/{propertyId}")
  ResponseEntity<ApiResponse<Map<String, Object>>> delete(
      @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipalDto userDetails,
      @Parameter(
              name = "propertyId",
              description = "매물 고유 인덱스 값",
              example = "123",
              required = true,
              in = ParameterIn.PATH)
          @PathVariable("propertyId")
          Long propertyId);

  @Operation(summary = "매물 상세 조회", description = "경로 변수로 전달된 `propertyId`에 해당하는 매물의 상세 정보를 조회한다.")
  @io.swagger.v3.oas.annotations.responses.ApiResponses({
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
                          "images": [
                            {
                              "imageId": 5,
                              "url": "https://zipzip-bucket.s3.amazonaws.com/images/1ced2238-b5ce-41c8-bea4-db7794b9198f.png",
                              "order": 1
                            }
                          ],
                          "propertyId": 15,
                          "planId": 1,
                          "planName": "기본 계획",
                          "folderId": 1,
                          "folderName": "기본폴더",
                          "feeling": "GOOD",
                          "propertyName": "괜찮은 원룸",
                          "memo": "제일 우선 순위",
                          "referenceUrl": "https://naver.me/Gub7uDq5",
                          "address": "35 제주특별자치도 서귀포시 안덕면 중산간서로1615번길 251-24",
                          "detailAddress": "105층",
                          "longitude": 128.981411608041,
                          "latitude": 35.098237529973,
                          "contractType": "MONTHLY_RENT",
                          "houseType": "ETC",
                          "depositBig": 7,
                          "depositSmall": 10,
                          "managementFee": 10,
                          "moveInInfo": "9월초",
                          "checklist": {
                            "categories": [
                              { "order": 0, "name": "필수 확인" },
                              { "order": 1, "name": "메인 공간" },
                              { "order": 2, "name": "창문" },
                              { "order": 3, "name": "욕실" },
                              { "order": 4, "name": "건물" }
                            ],
                            "sections": [
                              {
                                "categoryId": 0,
                                "categoryName": "필수 확인",
                                "memo": "필수 확인 메모 입니다!!",
                                "items": [
                                  {
                                    "id": 2,
                                    "question": "전체적으로 깔끔한 편인가요?",
                                    "description": "벽지와 장판 오염이 많을 경우 도배 가능한지 문의하세요"
                                  },
                                  { "id": 4, "question": "벌레 출몰 위험은 없나요?", "description": "주방에 까만 점이 있거나, 약을 둔 흔적이 있나요?" },
                                  { "id": 8, "question": "바람이 잘 통하나요?" },
                                  { "id": 13, "question": "수압이 좋은 집인가요?", "description": "세면대, 샤워기를 틀어 놓고 변기 물을 내려보세요" },
                                  { "id": 23, "question": "보안 상태는 괜찮은가요?", "description": "도어락·방범창 상태와 CCTV 위치를 확인하세요" },
                                  { "id": 21, "question": "건물에 악취나 벌레가 있을만한 가게가 있나요?" }
                                ]
                              },
                              {
                                "categoryId": 4,
                                "categoryName": "메인 공간",
                                "items": [
                                  { "id": 1, "question": "너무 노후화되진 않았나요?" },
                                  { "id": 3, "question": "체감 면적은 넓은가요?" },
                                  { "id": 5, "question": "벽간 소음은 괜찮은가요?", "description": "벽을 두드렸을 때, '통통' 소리가 난다면 주의하세요" },
                                  { "id": 6, "question": "가구 옵션 구성과 상태는 어떤가요?", "description": "수리나 청소가 필요한지 꼼꼼하게 살펴보세요" },
                                  { "id": 7, "question": "인터넷이 설치되어 있나요?", "description": "휴대폰도 잘 터지는지 확인하세요" }
                                ]
                              },
                              {
                                "categoryId": 1,
                                "categoryName": "창문",
                                "memo": "메인 공간 메모입니다",
                                "items": [
                                  { "id": 9, "question": "햇빛이 잘 드나요?", "description": "남향 > 남동향 > 동향 > 남서향 > 서향 > 북향" },
                                  { "id": 10, "question": "뷰는 괜찮은가요?", "description": "다른 건물과 너무 가깝지는 않은지 확인하세요!" }
                                ]
                              },
                              {
                                "categoryId": 2,
                                "categoryName": "욕실",
                                "items": [
                                  { "id": 11, "question": "배수구 악취는 없나요?", "description": "화장실 환풍기와 창문 여부를 확인하세요" },
                                  { "id": 12, "question": "온수가 잘 나오나요?", "description": "보일러 연식이 10년 이상이면 온수·가스비를 주의하세요" },
                                  { "id": 14, "question": "배수 상태는 괜찮은가요?", "description": "물 받아두고 다시 빼보세요" }
                                ]
                              },
                              {
                                "categoryId": 3,
                                "categoryName": "건물",
                                "memo": "욕실 공간 메모입니다",
                                "items": [
                                  { "id": 15, "question": "층수는 어떻게 되나요?" },
                                  { "id": 16, "question": "엘레베이터는 있나요?" },
                                  { "id": 22, "question": "소음 발생 가능성은 없나요?", "description": "대로변에 위치하거나 인근에 공사장이 있나요?" },
                                  { "id": 17, "question": "담배나 찌든 냄새 등 악취가 있진 않나요?" },
                                  { "id": 18, "question": "주차 공간이 있나요?" },
                                  { "id": 19, "question": "가파른 언덕에 있지는 않나요?" },
                                  { "id": 20, "question": "인근에 편의시설이 잘 갖춰져 있나요?", "description": "편의점, 코인빨래방 등이 가까운 곳에 있나요?" }
                                ]
                              }
                            ]
                          }
                        }
                      }
                      """))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "404",
        description = "존재하지 않는 매물 / 권한 없음",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples =
                    @ExampleObject(
                        name = "Not Found 예시",
                        value =
                            """
                      {
                        "code": "74001",
                        "message": "존재하지 않는 매물입니다",
                        "data": null
                      }
                      """)))
  })
  @GetMapping("/{propertyId}")
  ResponseEntity<ApiResponse<PropertyDetailResponse>> getProperty(
      @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipalDto userDetails,
      @Parameter(
              name = "propertyId",
              description = "매물 고유 인덱스 값",
              required = true,
              example = "15",
              in = ParameterIn.PATH)
          @PathVariable("propertyId")
          Long propertyId);

  @Operation(
      summary = "매물 수정",
      description =
          """
          경로 변수의 `propertyId` 대상 매물을 수정한다.
          - **이미지 추가**: 폼 파트 `image`(여러 장 가능, 같은 키 반복 첨부)
          - **이미지 삭제**: `data.deletedImageIdList`(이미지 ID 리스트)
          - 나머지 필드는 `data` JSON 파트로 전송 (옵션 필드를 `""`로 보내면 검증 수행)
          """)
  @io.swagger.v3.oas.annotations.parameters.RequestBody(
      required = true,
      content =
          @Content(
              mediaType = "multipart/form-data",
              schema = @Schema(implementation = UpsertPropertyMultipartDocs.class),
              examples =
                  @ExampleObject(
                      name = "폼데이터 예시",
                      value =
                          """
                  {
                    "data": {
                      "deletedImageIdList": [9, 10],
                      "feeling":"BAD",
                      "propertyName":"안 괜찮은 원룸",
                      "memo":"이 집은 될 수 있으면 피할 것!",
                      "address":"경상북도 울진군",
                      "detailAddress":"앞마당",
                      "longitude":"128.981411608042",
                      "latitude":"35.098237529977",
                      "contractType":"JEONSE",
                      "houseType":"ETC",
                      "depositBig":1,
                      "depositSmall":2,
                      "moveInInfo":"30일 지나서",
                      "categoryMemoList":[
                        {"categoryId":1,"memo":"메인 공간 메모입니다"},
                        {"categoryId":2,"memo":"추가된 메모 입니다"}
                      ],
                      "folderId":1
                    },
                    "image": "<<새로 추가할 이미지 파일들: 같은 키 'image'로 반복 첨부>>"
                  }
                  """)))
  @io.swagger.v3.oas.annotations.responses.ApiResponses({
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
                        "data": {}
                      }
                      """))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "404",
        description = "존재하지 않는 매물 / 권한 없음",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples =
                    @ExampleObject(
                        name = "Not Found 예시",
                        value =
                            """
                      {
                        "code": "74001",
                        "message": "존재하지 않는 매물입니다",
                        "data": null
                      }
                      """))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "400",
        description = "요청 값 검증 실패 / 매물에 속하지 않은 이미지 ID 포함",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples =
                    @ExampleObject(
                        name = "이미지 소유권 불일치 예시",
                        value =
                            """
                      {
                        "code": "74002",
                        "message": "해당 매물 메모에 속해있지 않은 이미지가 요청되었습니다",
                        "data": null
                      }
                      """)))
  })
  @PatchMapping("/{propertyId}")
  ResponseEntity<ApiResponse<Map<String, Object>>> update(
      @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipalDto userDetails,
      @Parameter(
              name = "propertyId",
              description = "매물 고유 인덱스 값",
              required = true,
              example = "15",
              in = ParameterIn.PATH)
          @PathVariable("propertyId")
          Long propertyId,
      @RequestPart(value = "image", required = false) List<MultipartFile> files,
      @Validated @RequestPart(value = "data") UpsertPropertyRequest request);

  @Operation(
      summary = "최근 매물 조회",
      description =
          """
            사용자가 최근에 등록한 매물들을 최신 순으로 조회한다.
            - 응답 데이터는 `size` 개수만큼 반환된다 (기본값 10).
            - 매물에 첨부된 이미지 중 첫 번째(`order=1`) 이미지를 `imageUrl`에 담아 제공한다.
            - 이미지가 없는 경우 `imageUrl`은 `null`일 수 있다.
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
                        name = "성공 예시",
                        value =
                            """
                        {
                          "code": "20000",
                          "message": "성공했습니다",
                          "data": [
                            {
                              "propertyId": 47,
                              "imageUrl": null,
                              "feeling": "BAD",
                              "title": "11번째 집!",
                              "depositBig": 0,
                              "depositSmall": 1,
                              "managementFee": null,
                              "contractType": "JEONSE",
                              "planName": "기본 계획",
                              "folderName": "관악산근처"
                            },
                            {
                              "propertyId": 42,
                              "imageUrl": "https://zipzip-bucket.s3.amazonaws.com/images/c07f1844-d1c6-414d-bd60-78c21d79ec6e.jpeg",
                              "feeling": "SOSO",
                              "title": "관악산",
                              "depositBig": 100,
                              "depositSmall": 10,
                              "managementFee": null,
                              "contractType": "JEONSE",
                              "planName": "기본 계획",
                              "folderName": "관악산근처"
                            }
                          ]
                        }
                        """))),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "401",
        description = "인증 실패 (로그인 필요)",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples =
                    @ExampleObject(
                        name = "인증 실패 예시",
                        value =
                            """
                        {
                          "code": "40100",
                          "message": "인증이 필요합니다",
                          "data": null
                        }
                        """)))
  })
  @GetMapping("/recent")
  ResponseEntity<ApiResponse<List<RecentPropertyModel>>> getRecentProperties(
      @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipalDto userDetails,
      @Parameter(name = "size", description = "응답 받을 매물 메모 갯수 (기본값 10, 옵션)", example = "5")
          @RequestParam(name = "size", defaultValue = "10")
          int size);
}
