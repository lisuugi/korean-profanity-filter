# 설계 철학
## 아키텍쳐 개요
전체적인 클래스 구조와 각 컴포넌트(ProfanityFilter, FileLoader, Matcher 등)의 역할에 대해 설명합니다.

## 디자인 패턴
### 책임 연쇄 패턴의 체이닝 방식에 대한 고찰
~~~java
private static Filter initFilter() {
    Filter specialCharTransitionFilter = new SpecialCharTransitionFilter();
    Filter numberFilter = new NumberFilter();
    Filter concatFilter = new ConcatFilter();

    // 명시적인 체이닝
    specialCharTransitionFilter.setNext(numberFilter);
    numberFilter.setNext(concatFilter);

    // 한 번에 체이닝
    specialCharTransitionFilter
            .then(numberFilter)
            .then(concatFilter);

    return specialCharTransitionFilter;
}
~~~
명시적인 체이닝 방식과 한 번에 체이닝 하는 방식 두 가지를 고려해 보았습니다.
한 번에 체이닝 방식이 보기에 깔끔하고, 여러 줄을 작성하지 않아도 간편하게 작성할 수 있다는 장점이 있습니다.
하지만 가운데에 선택적으로 필터가 들어가는 경우처럼 분기점이 존재하는 경우에는 한번에 체이닝 하는 것이 어렵습니다.
또한 연쇄적으로 일어나기 때문에 특정 필터를 빼먹더라도 디버깅도 어려워진다는 문제가 발생합니다.
따라서 저는 명시적인 체이닝 방식을 선택했습니다.


### 빌더 패턴의 활용과 기본값 사용 유도
#### 문제점
객체 생성 이후 필터를 직접 주입하는 방식도 고려해 보았으나, 기본값을 넣어도 실수로 추가로 필터값을 수정할 수 있는 문제점이 존재하였습니다.

#### 해결 방안
빌더 패턴을 활용하여 사용자가 직접 메서드 체이닝을 통해 필터 기능을 선택적으로 넣어 커스텀 필터를 사용할 수 있도록 하였고, 기본값 초기화 createDefault() 메서드를 통해 간편하게 객체를 생성할 수 있도록 하였습니다.


### 퍼사드 패턴을 활용한 외부 라이브러리 간단 사용
#### 문제점
아호코라식 라이브러리를 직접 불러와 사용하는 경우, 유사한 기능의 다른 객체가 추가되었을 경우 중복해서 작성해야 하는 문제가 존재하고, 상세 로직을 작성하기 때문에 혼란을 주는 문제가 발생합니다.

#### 해결 방안
아호코라식 라이브러리를 로직에 맞춰서 간편하게 불러오기 위해, AhoCorasikMatcher 퍼사드 객체를 만들어 각 행동 별로 API를 만들어 아호코라식 라이브러리의 상세 로직을 캡슐화하였습니다.


### 전략 패턴을 활용한 욕설 단어 감지 로직 추상화
#### 문제점
욕설 단어 감지는 같은 API라도 다양한 방식으로 구현될 수 있고, 여러 가지 감지 로직을 복합적으로 적용할 수 있습니다. 이 과정에서 각 감지 프로세스를 모두 하나하나 생성자 주입을 하는 경우 문제가 발생할 수 있습니다.

#### 해결 방안
MatchingStrategy 인터페이스를 정의하여 공통 로직을 구현하는 방식으로 객체 생성을 List<MatchingStrategy> 와 같이 묶는 방식을 통해 간편하게 추가가 가능하게 됩니다.


## 고민의 흔적
### 멀티 모듈 구조를 고려했다가 단일 모듈로 결정한 이유

### internal 패키지 대신 패키지-프라이빗을 선택한 이유 등 설계 과정에서의 트레이드오프를 기록합니다.

### Regex를 필터로 포함시킬지, Matcher로 포함시킬지.
정규 표현식은 기본적으로 성능이 느리기 때문에, 

애초에 생성할 때부터 Regex 사용을 정하는 거랑, containsWords하고 containsWordsWithRegex 이렇게 사용 단계에서 분리하는 거랑 어떤게 좋아보여? Regex도 아호코라식하고 별개의 하나의 처리 로직이라서 Filter에 넣는 거는 좀 아닌 것 같아.

기능이 늘어날 때마다 메서드가 늘어나는 문제 발생.