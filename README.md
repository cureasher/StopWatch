# StopWatch
## 목표
- 스레드를 활용한 스톱워치 만들기
- 시, 분, 초를 timer를 사용해 만들어라
- 현재 시간과 마지막 시간 구간 빨강, 파랑으로 텍스트 색상 변경

## 구현방법
- UI 작업
- 리사이클러뷰 구현
    - root가 아래에서부터 나오도록 reverseLayout true로 세팅
    - recordList를 전역으로 만들고 최대값, 최소값 recordTime 구현
- 스레드 1개 사용
    - Timer 클래스 사용

## 레이아웃
- total time과 lap time
    - Constrain Layout으로 그림
    - 시, 분, 초, 밀리초
- RecyclerView
    - 구간기록(구간, 랩타임 시간, 전체 시간)
- 구간기록 버튼, 시작 버튼
    - 시작을 누르면 버튼은 구간기록과 중지로 바뀜
    - 중지를 누르면 버튼은 초기화와 계속으로 바뀜
