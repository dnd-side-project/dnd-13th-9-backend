package com.example.dnd_13th_9_be.checklist.presentation.docs;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.example.dnd_13th_9_be.checklist.presentation.dto.ChecklistResponse;
import com.example.dnd_13th_9_be.global.response.ApiResponse;
import com.example.dnd_13th_9_be.user.application.dto.UserPrincipalDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "체크리스트 조회", description = "사용자 맞춤 체크리스트 카테고리/섹션/아이템 조회")
public interface ChecklistDocs {

  @Operation(
      summary = "체크리스트 조회",
      description =
          """
          사용자별 체크리스트를 조회합니다.
          - Path: `/api/checklist`
          - Method: `GET`
          - Query Params: 없음
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
                          "categories": [
                            { "order": 0, "name": "필수 확인" },
                            { "order": 1, "name": "메인 공간" },
                            { "order": 2, "name": "창문" },
                            { "order": 3, "name": "욕실" },
                            { "order": 4, "name": "건물" }
                          ],
                          "sections": [
                            {
                              "categoryName": "필수 확인",
                              "items": [
                                { "id": 2,  "question": "전체적으로 깔끔한 편인가요?", "description": "벽지와 장판 오염이 많을 경우 도배 가능한지 문의하세요" },
                                { "id": 3,  "question": "체감 면적은 넓은가요?" },
                                { "id": 4,  "question": "벌레 출몰 위험은 없나요?", "description": "주방에 까만 점이 있거나, 약을 둔 흔적이 있나요?" },
                                { "id": 8,  "question": "바람이 잘 통하나요?" },
                                { "id": 13, "question": "수압이 좋은 집인가요?", "description": "세면대, 샤워기를 틀어 놓고 변기 물을 내려보세요" },
                                { "id": 23, "question": "보안 상태는 괜찮은가요?", "description": "도어락·방범창 상태와 CCTV 위치를 확인하세요" },
                                { "id": 21, "question": "건물에 악취나 벌레가 있을만한 가게가 있나요?" }
                              ]
                            },
                            {
                              "categoryName": "메인 공간",
                              "items": [
                                { "id": 1, "question": "너무 노후화되진 않았나요?" },
                                { "id": 5, "question": "벽간 소음은 괜찮은가요?", "description": "벽을 두드렸을 때, '통통' 소리가 난다면 주의하세요" },
                                { "id": 6, "question": "가구 옵션 구성과 상태는 어떤가요?", "description": "수리나 청소가 필요한지 꼼꼼하게 살펴보세요" },
                                { "id": 7, "question": "인터넷이 설치되어 있나요?", "description": "휴대폰도 잘 터지는지 확인하세요" }
                              ]
                            },
                            {
                              "categoryName": "창문",
                              "items": [
                                { "id": 9,  "question": "햇빛이 잘 드나요?", "description": "남향 > 남동향 > 동향 > 남서향 > 서향 > 북향" },
                                { "id": 10, "question": "뷰는 괜찮은가요?", "description": "다른 건물과 너무 가깝지는 않은지 확인하세요!" }
                              ]
                            },
                            {
                              "categoryName": "욕실",
                              "items": [
                                { "id": 11, "question": "배수구 악취는 없나요?", "description": "화장실 환풍기와 창문 여부를 확인하세요" },
                                { "id": 12, "question": "온수가 잘 나오나요?", "description": "보일러 연식이 10년 이상이면 온수·가스비를 주의하세요" },
                                { "id": 14, "question": "배수 상태는 괜찮은가요?", "description": "물 받아두고 다시 빼보세요" }
                              ]
                            },
                            {
                              "categoryName": "건물",
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
                      """)))
  })
  ResponseEntity<ApiResponse<ChecklistResponse>> getChecklist(
      @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipalDto userDetails);
}
