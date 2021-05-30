# 조선대 학생들을 AR Navigation 어플리케이션(Chosun Navi)

## 프로젝트명
**Chosun Navi**<br>
<br>

## 프로젝트 소개
**조선대 학생들을 위한 AR 네비게이션**<br>
[발표 ppt로 전체 내용 확인하기](./document/캡스톤중간발표.pdf)
<br>

## 기획 의도
조선대 학생들이 사용 가능한 지도들의 문제점들을 파악하고 사용자를 위한 보행자용 AR네비게이션의 구현
- 캠퍼스 맵 : 학교 홈페이지에서 제공하는 지도, 지도가 고정적이며 자신의 위치, 방향에 대해 파악하기 어려움. 홈페이지에 지속적으로 접속해야하는 번거로움
- 네이버 지도: 위치, 방향에대해 파악하기 쉽지만, 큰 건물에 대해서만 목적지 설정이 가능하며, 네비게이션기능은 큰 경로로만 안내함. 도보경로 부족
<br>

## 세부 내용
### 기술 스택 및 개발 환경
![기술 스택](https://user-images.githubusercontent.com/59193640/120098590-c2e22d80-c171-11eb-8549-b9ff0c8f9f19.png)

### 목적지 선택 기능
- 건물, 학과, 교수님에 대한 정보로 목적지를 선택 가능
- 키워드만 검색해도 검색 내용이 나오도록 설정(자동완성 기능)([DestinationActivity.java](./app/src/main/java/com/example/capstone_ui_1/DestinationActivity.java))
```java
 searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s.toString());

                return false;
            }
        });
```
-  자동완성 필터([CustomAdapter.java](./app/src/main/java/com/example/capstone_ui_1/Data/CustomAdapter.java))
```java
    private Filter FilterUser = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String searchText = charSequence.toString().toLowerCase();
            ArrayList<ChosunDTO> tempList = new ArrayList<>();
            if (searchText.length() == 0 || searchText.isEmpty()) {
                tempList.addAll(arrayListFull);
            } else {
                for (ChosunDTO item : arrayListFull) {
                    if (item.getBuilding().contains(searchText) || (item.getMajor().contains(searchText) || (item.getProfessor().contains((searchText))))) {
                        tempList.add(item);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = tempList;
            return filterResults;
        }
```

### 지도에서 목적지 선택 기능
- Mapbox 지도를 활용
- 지도에서 직접 목적지 선택하기([MainActivity2.java](./app/src/main/java/com/example/capstone_ui_1/MainActivity2.java))
```java
@Override
    public boolean onMapClick(@NonNull LatLng point) {
        if (destinationMarker != null) {
            mapboxMap.removeMarker(destinationMarker);
        }
        destinationMarker = mapboxMap.addMarker(new MarkerOptions().position(point));//마커 추가
        destination = Point.fromLngLat(point.getLongitude(), point.getLatitude());//클릭한곳의 좌표
        Log.e(TAG, "destinationPosition : " + destination);
        origin = Point.fromLngLat(Lo, La);//현재 좌표
        getRoute_navi_walking(origin, destination);
        startButton.setEnabled(true);   //네비게이션 버튼 활성화
        startButton.setBackgroundResource(R.color.mapboxBlue);
        arButton.setEnabled(true);
        arButton.setBackgroundResource(R.color.mapboxBlue);
        return false;
    }
```
### 네비게이션 기능
- Mapbox Studio를 활용한 교내 지도 제작
- Directions API를 활용한 경로 및 정보 제공([MainActivity.java](./app/src/main/java/com/example/capstone_ui_1/MainActivity.java))
```java
 private void getRoute_navi_walking(Point ori, Point dest) {
        Log.e(TAG, "get_Route_navi_walking실행");
        NavigationRoute.builder(this)
                .accessToken(Mapbox.getAccessToken())  
                .profile(DirectionsCriteria.PROFILE_WALKING)
                .origin(ori)
                .destination(dest)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        // You can get the generic HTTP info about the response
                        Log.d(TAG, "Response code: " + response.code());
                        if (response.body() == null) {
                            Log.e(TAG, "No routes found, make sure you set the right user and access token.");
                            return;
                        } else if (response.body().routes().size() < 1) {
                            Log.e(TAG, "No routes found");
                            return;
                        }

                        currentRoute = response.body().routes().get(0);

                        // Draw the route on the map
                        if (navigationMapRoute != null) {
                            navigationMapRoute.removeRoute();
                        } else {
                            navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap, R.style.NavigationMapRoute);
                        }
                        navigationMapRoute.addRoute(currentRoute);
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                        Log.e(TAG, "Error: " + throwable.getMessage());
                    }
                });
    }
```
- OpenStreetMap을 활용해 실제 보행 가능 경로 추가
- Directions.prefab을 활용해 AR 경로 제공

### 최종 화면
<p>
    <img src="https://user-images.githubusercontent.com/59193640/120099416-4736af80-c176-11eb-8385-280ad15c7ffa.jpg" width="400" height="640">
    <img src="https://user-images.githubusercontent.com/59193640/120099417-4867dc80-c176-11eb-8aa9-48afd248a770.jpg" width="400" height="640">
</p>
<p>
    <img src="https://user-images.githubusercontent.com/59193640/120099419-49007300-c176-11eb-8c25-c23ab0e582c9.jpg" width="400" height="640">
    <img src="https://user-images.githubusercontent.com/59193640/120099430-561d6200-c176-11eb-963c-476653da2864.jpg" width="400" height="640">
</p>
<br>

## 결론
- 실제 사용 중인 서비스의 부족한 점을 분석하고 구현
- 구현 과정에 있어 다양한 기술 스택의 사용
- 구현 과정에 있어 발생했던 문제점들에 대해 협의를 통한 해결책 모색
- 초기 목표로 했던 바 모두 달성
<br>

## 참고 문헌
- [Mapbox Tutorial](https://docs.mapbox.com/help/tutorials/)
- [Mapbox Documentation](https://docs.mapbox.com/)
- [Android Developer Guide](https://developer.android.com/guide?hl=ko)
- [StackOverflow](https://stackoverflow.com/)
- Mapbox와 주고받은 메일