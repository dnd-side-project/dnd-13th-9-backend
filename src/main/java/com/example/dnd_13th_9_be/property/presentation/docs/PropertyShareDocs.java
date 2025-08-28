package com.example.dnd_13th_9_be.property.presentation.docs;

import com.example.dnd_13th_9_be.global.response.ApiResponse;
import com.example.dnd_13th_9_be.property.presentation.dto.response.PropertyDetailResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "매물 공유", description = "매물 공유(Property Share) 관련 API")
public interface PropertyShareDocs {
    @Operation(
        summary = "매물 메모 공유 화면 데이터 조회",
        description =
            """
            공유 토큰(shareLink)으로 매물 메모 상세 데이터를 조회합니다.
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
                          "data": {
                            "images": [
                              { "imageId": 13, "url": "https://zipzip-bucket.s3.amazonaws.com/images/58be7686-3fcb-4655-b3aa-fe9e713c0ab7.jpeg", "order": 1 },
                              { "imageId": 14, "url": "https://zipzip-bucket.s3.amazonaws.com/images/84a2ccc2-52c9-4f5f-b1b1-c7c37e926887.jpg", "order": 2 }
                            ],
                            "propertyId": 35,
                            "planId": 3,
                            "planName": "기본 계획",
                            "folderId": 3,
                            "folderName": "기본 폴더",
                            "feeling": "BAD",
                            "propertyName": "안 괜찮은 원룸",
                            "memo": "이 집은 될 수 있으면 피할 것!",
                            "referenceUrl": null,
                            "address": "경상북도 울진군",
                            "detailAddress": "앞마당",
                            "longitude": 128.981411608042,
                            "latitude": 35.098237529977,
                            "contractType": "JEONSE",
                            "houseType": "ETC",
                            "depositBig": 1,
                            "depositSmall": 2,
                            "managementFee": null,
                            "moveInInfo": "30일 지나서",
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
                                  "items": [
                                    { "itemId": 1, "question": "너무 노후화되진 않았나요?" },
                                    { "itemId": 21, "question": "건물에 악취나 벌레가 있을만한 가게가 있나요?" }
                                  ]
                                },
                                {
                                  "categoryId": 4,
                                  "categoryName": "메인 공간",
                                  "items": [
                                    { "itemId": 2, "question": "전체적으로 깔끔한 편인가요?", "description": "벽지와 장판 오염이 많을 경우 도배 가능한지 문의하세요" },
                                    { "itemId": 3, "question": "체감 면적은 넓은가요?" },
                                    { "itemId": 4, "question": "벌레 출몰 위험은 없나요?", "description": "주방에 까만 점이 있거나, 약을 둔 흔적이 있나요?" },
                                    { "itemId": 5, "question": "벽간 소음은 괜찮은가요?", "description": "벽을 두드렸을 때, '통통' 소리가 난다면 주의하세요" },
                                    { "itemId": 6, "question": "가구 옵션 구성과 상태는 어떤가요?", "description": "수리나 청소가 필요한지 꼼꼼하게 살펴보세요" },
                                    { "itemId": 7, "question": "인터넷이 설치되어 있나요?", "description": "휴대폰도 잘 터지는지 확인하세요" }
                                  ]
                                },
                                {
                                  "categoryId": 1,
                                  "categoryName": "창문",
                                  "memo": "메인 공간 메모입니다",
                                  "items": [
                                    { "itemId": 8, "question": "바람이 잘 통하나요?" },
                                    { "itemId": 9, "question": "햇빛이 잘 드나요?", "description": "남향 > 남동향 > 동향 > 남서향 > 서향 > 북향" },
                                    { "itemId": 10, "question": "뷰는 괜찮은가요?", "description": "다른 건물과 너무 가깝지는 않은지 확인하세요!" }
                                  ]
                                },
                                {
                                  "categoryId": 2,
                                  "categoryName": "욕실",
                                  "memo": "추가된 메모 입니다",
                                  "items": [
                                    { "itemId": 11, "question": "배수구 악취는 없나요?", "description": "화장실 환풍기와 창문 여부를 확인하세요" },
                                    { "itemId": 12, "question": "온수가 잘 나오나요?", "description": "보일러 연식이 10년 이상이면 온수·가스비를 주의하세요" },
                                    { "itemId": 13, "question": "수압이 좋은 집인가요?", "description": "세면대, 샤워기를 틀어 놓고 변기 물을 내려보세요" },
                                    { "itemId": 14, "question": "배수 상태는 괜찮은가요?", "description": "물 받아두고 다시 빼보세요" }
                                  ]
                                },
                                {
                                  "categoryId": 3,
                                  "categoryName": "건물",
                                  "items": [
                                    { "itemId": 15, "question": "층수는 어떻게 되나요?" },
                                    { "itemId": 16, "question": "엘레베이터는 있나요?" },
                                    { "itemId": 22, "question": "소음 발생 가능성은 없나요?", "description": "대로변에 위치하거나 인근에 공사장이 있나요?" },
                                    { "itemId": 23, "question": "보안 상태는 괜찮은가요?", "description": "도어락·방범창 상태와 CCTV 위치를 확인하세요" },
                                    { "itemId": 17, "question": "담배나 찌든 냄새 등 악취가 있진 않나요?" },
                                    { "itemId": 18, "question": "주차 공간이 있나요?" },
                                    { "itemId": 19, "question": "가파른 언덕에 있지는 않나요?" },
                                    { "itemId": 20, "question": "인근에 편의시설이 잘 갖춰져 있나요?", "description": "편의점, 코인빨래방 등이 가까운 곳에 있나요?" }
                                  ]
                                }
                              ]
                            }
                          }
                        }
                        """))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "500",
            description = "복호화 실패 (유효하지 않은 공유 토큰)",
            content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class),
                examples =
                @ExampleObject(
                    name = "복호화 실패 예시",
                    value =
                        """
                        {
                          "code": "51001",
                          "message": "복호화에 실패했습니다",
                          "data": null
                        }
                        """)))
    })
    ResponseEntity<ApiResponse<PropertyDetailResponse>> getProperty(
        @PathVariable("shareLink") String shareLink);
}
