package com.example.capstone_ui_1;

public class Converter {
    //건물명을 영어로 변환해주는 컨버터
    private String bName;

    public void convertBuildingToEng(String s) {
        switch (s) {
            case "공과대학 제1공학관":
                bName = "engineering_one";
                break;
            case "공과대학 제2공학관":
                bName = "engineering_two";
                break;
            case "항공우주대학":
                bName = "space";
                break;
            case "IT융합대학":
                bName = "it";
                break;
            case "사회과학대학":
                bName = "social";
                break;
            case "미술대학":
                bName = "art";
                break;
            case "체육대학":
                bName = "physical";
                break;
            case "의과대학 1호관":
                bName = "medical_one";
                break;
            case "의과대학 2호관":
                bName = "medical_two";
                break;
            case "의과대학 3호관":
                bName = "medical_three";
                break;
            case "조선간호대학":
                bName = "nurse";
                break;
            case "치과대학":
                bName = "dental";
                break;
            case "약학대학":
                bName = "medicine";
                break;
            case "본관 정문":
                bName = "main";
                break;
            case "자연과학관":
                bName = "natural";
                break;
            case "법과대학":
                bName = "law";
                break;
            case "경상대학":
                bName = "business";
                break;
            case "도서관":
                bName = "library";
                break;
            case "국제관":
                bName = "international";
                break;
            case "서석홀":
                bName = "s_hall";
                break;
            case "학생회관":
                bName = "student";
                break;
            case "교수연구동":
                bName = "professor_research";
                break;
            case "글로벌하우스":
                bName = "global";
                break;
            case "남자 백학":
                bName = "backhak";
                break;
            case "여자 백학":
                bName = "backhak";
                break;
            case "생명공학관":
                bName = "bio";
                break;
            case "입석홀":
                bName = "i_hall";
                break;
            case "조선대학교 병원":
                bName = "hospital";
                break;
            case "창업보육센터":
                bName = "startup";
                break;
            case "해오름관(e-스포츠)":
                bName = "esports";
                break;
            case "학군단(ROTC)":
                bName = "rotc";
                break;
            case "솔마루":
                bName = "maru";
                break;
            default:
                bName = "no_information";
                break;
        }
    }
    public String getBName() {
        return bName;
    }
}
