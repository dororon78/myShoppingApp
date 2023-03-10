# **장보기 도우미**
"장보기 도우미" 앱은 마트 방문 전에 구매 목록을 기록 및 관리하여 빠뜨리는 품목 없도록 도와줍니다.
앱 실행부터 기록까지 신속하게 접근할 수 있어 사용성이 높으며,  장을 보는 도중에 가시화된 리스트를 확인하고 구매한 목록을 수정할 수 있습니다.
그리고 "장보기 도우미" 앱은 다른 결제 앱을 호출하여 신속하게 결제할 수 있게 도와줍니다. 

## **기능 요약**
1. 구매목록을 기록하고 장을 보며 구매한 품목을 관리할 수 있습니다.
2. 장보기가 끝난 시점에 완료 품목을 삭제하고 결제 앱을 바로 실행할 수 있습니다.
(현재 삼성페이, 이마트페이 지원)

## **개발 동기**
주로 식품이나 생필품을 소비하면서 거의 소진한 상태를 확인하고 재구매를 생각하거나, 사용중에 불편함을 느껴 교체를 생각하는 경우도 있습니다.

생각하는 즉시 구매를 위해 이동할 수 있거나, 구매한다면 누락이 발생할 가능성은 작을 것입니다.
하지만, 실생활에서는 구매를 생각하는 시점과 구매 시점은 다릅니다. 실제 구매를 위해 마트에 방문하는 시점은 품목의 긴급도와 생활 동선에 따라 결정합니다. 

사람의 가치관에 따라 다르지만, 식품과 치약 등 생활에 밀접하여 없어서는 안 되는 물품의 긴급도는 높으며, 메모지나 마우스 패드와 같이 없어도 생활에 큰 지장이 없는 품목은 긴급도가 낮습니다.
긴급도가 낮은 품목은 마트를 방문할 동기로 충분하지 않아 방문 겸 구매를 시도합니다. 

위와 같이 물품별 우선순위의 차이를 무의식적으로 결정하고, 기억하며 다음번 마트 방문 시 구매를 잊지 않기 위해 노력합니다.
하지만 안타깝게도 막상 장을 보면 우선순위가 높은 순서로 기억에 남아 낮은 우선순위의 품목은 누락하고 귀가합니다.
그리고, 귀가 후 불편함을 느끼며 다시 구매를 떠올리게 됩니다.

물품 구매는 생활에 필요한 행위이지만, 효율적인 수행을 위해 투입해야 하는 자원이 많아 불편하였고,
앱 개발을 통해 얻을 수 있는 장점이 많다고 판단하여 장보기를 보조해줄 앱을 개발하였습니다. 

## **배우고 느낀 점**
### **Android Jetpack**
Android Jetpack Compose를 사용해 앱을 구현하여 처음으로 Jetpack Compose를 경험해볼 수 있었습니다. 이번 프로젝트에서 Jetpack Compose를 사용하며 느낀 점을 간략하게 정리했습니다.

#### 높은 가독성:  
Jetpack Compose는 XML로 구현하는 방식보다 가독성이 높았습니다. 같은 파일 안에서 레이아웃과 동작을 확인할 수 있어서 XML과 구현 파일을 오가며 발생하는 작은 방해 요소가 사라졌습니다. 

View가 복잡해진 상태에서 모듈화하는 과정도 일반적인 로직 모듈화와 비슷하여 모듈화가 수월하였습니다.

#### 적은 코드:  
코드 작성량이 적어 물리적으로 빠르게 개발할 수 있었습니다. 특히, 기존 방식에서는 Recyclerview를 사용할 상황에서 Compose는 LazyColumn을 제공하였고, Recycler view는 Button을 만드는 수준으로 빠르게 구현할 수 있었습니다. 
  
#### Kolint과의 상성:  
Compose의 함수형 선언방식이 kotlin의 람다 핸들링과 잘 어우러져 깔끔한 코드를 작성하기 좋았습니다. 하지만, onClick과 같은 이벤트 핸들링을 후행 람다로 사용하면 Compose 함수와 비슷하여 오히려 가독성이 낮아지는 경험을 했습니다. 후행람다는 Compose 함수만을 위해 사용하여 높은 가독성을 유지하고, 이벤트 핸들링을 위한 함수는 함수 타입의 변수로 사용하는 것이 코드를 깔끔하게 유지할 수 있었습니다. 

#### 안정성:  
일부 몇 가지 버그를 경험하면서 현업에서 사용할 때 작업에 변수가 생길 가능성이 높을 것 같습니다. 

### **프로젝트 구현**
#### 문제의식:
프로젝트를 빠르게 구현에 하기 위해서는 기술 스택보다 중요한 것이 문제의식임을 느꼈습니다. 이 프로젝트에서 문제의식이 명확했던 "물품 구매 누락"과 "결제 불편함"은 각각 "망각"과 "디바이스에 앱이 너무 많음"이었기 때문에 "기록"과 "결제 앱 즉시 연결"을 구현하여 해결하였습니다. 문제와 그 원인이 명확하여 해결 방향도 간단하게 결정하였습니다.

프로젝트 도중에 마트 방문의 동선을 최적화하는 목적으로 "매장 정보 등록"이라는 기능을 추가하려고 했습니다. 어떤 매장에서 어떤 물품이 있는지 명확하다면 동작할 수 있는 기능이지만, 매장에 있는 물품을 확인할 수 없는 상태에서는 무효한 해결책이었습니다. "매장" 기능의 깊이에 대해서 기획한 내용이 없다 보니 자연스럽게 "매장" 기능을 깊게 구현하기보단 "매장" 기능을 편하게 사용할 수 있는 확장 기능에 집중하게 되었습니다. 결국, "매장" 데이터를 앱에서 다룰 수 없는 정도만 가능하였고 사용자가 "매장" 기능을 통해 얻을 수 있는 가치는 없었습니다.

문제의식이 명확하지 않은 상태에서는 아무리 좋은 개발 도구를 사용해도 무의미한 것을 다시 느꼈습니다. 그리고, 작성자가 개발에 몰입하면 의식의 흐름에 따라 구현하는 습관이 있고 개발을 복잡하게 하는 단점이 있음을 깨달았습니다. 그래서, 몰입하는 현상을 막기 위해 간단한 기획 문서를 작성하며 생각을 정리하고, 실행을 위한 액션을 짧게 나눠서 정리하는 것으로 시도해보려고 합니다.

### **프로젝트 정리**
#### README:  
README를 작성은 "개발 이유", "개발 의도", "배운 점"을 다시 떠올리는 좋은 계기가 되었습니다. 의도했던 기능이 동기와 맞아 문제를 해결하는 데 도움이 되었는지 생각해볼 수 있었고, 배운 점을 회상하며 추후 활용 방법에 대해서 고민해볼 수 있었습니다. 평소에 Readme 작성에 크게 리소스를 사용한 적이 없어 작성에 많은 시간을 소모하였습니다. 프로젝트 리뷰를 위해서 관리 주기를 짧게는 것이 좋을 것 같다고 느꼈습니다.
#### 개발 블로그 운영의 필요성: 
경험한 내용을 글로만 설명하면 전달력이 떨어진다. 상황 설명과 해결책을 공유하고 기록하면서 README는 목적에 맞지 않아 별도로 블로그를 운영하면 좋겠다고 느꼈습니다.

## **개발 히스토리**
- 2022.12.20 ~ 2022.12.23 기능 개발 완료
- 2022.12.25 ~ 2022.12.27 리펙토링 및 테스트 코드 작성

## **이후 로드맵**
- Wear OS 지원
- iOS 지원
- 서버 구현
- 스토어 등록
