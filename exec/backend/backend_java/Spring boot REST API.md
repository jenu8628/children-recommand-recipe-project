# REST API

---

- ### 로그인 관리 [LoginController]

  - **_loginByParent(string <span style="color:greenYellow">parent_email</span>, string <span style="color:greenYellow">parent_pwd</span>)_**
    - **_<span style="color:#9E7BFF">Return <Map> <parent_id : TOKEN></span>_**
  - **_loginByChild(string <span style="color:greenYellow">child_id</span>)_**
    - **_<span style="color:#9E7BFF">Return <Map> <child_id : TOKEN></span>_**
  - **_findEmail(string <span style="color:greenYellow">parent_name</span>, string <span style="color:greenYellow">parent_tel</span>)_**
  - **_findPassword(string <span style="color:greenYellow">parent_email</span>, string <span style="color:greenYellow">parent_name</span>, string <span style="color:greenYellow">parent_birth</span>)_**

  

- ### 부모 관리 [ParentController]

  - **_joinParent(string <span style="color:greenYellow">parent_email</span>, string <span style="color:greenYellow">parent_pwd</span>, string <span style="color:greenYellow">parent_name</span>, string <span style="color:greenYellow">parent_tel</span>, string <span style="color:greenYellow">parent_birth</span>)_**
  - **_checkForDuplicateEmail(string <span style="color:#F75D59">input_email</span>)_**
  - **_leaveParent(string <span style="color:greenYellow">parent_id</span>)_**
  - **_verifyPassword(string <span style="color:greenYellow">parent_email</span>, string <span style="color:#F75D59">input_pwd</span>)_**
  - **_getParentInfo(string <span style="color:greenYellow">parent_id</span>)_**
    - <span style="color:#9E7BFF">**_Return <ParentDto>_**</span> 
  - **_modifyParentInfo(string <span style="color:greenYellow">parent_id</span>, string <span style="color:greenYellow">parent_pwd</span>, string <span style="color:greenYellow">parent_name</span>, string <span style="color:greenYellow">parent_tel</span>, string <span style="color:greenYellow">parent_birth</span>)_**

  

- ### 아이 관리 [ChildController]

  - **_addChild(string <span style="color:#38ACEC">child_parentId</span>, string <span style="color:greenYellow">child_area</span>, string <span style="color:greenYellow">child_schoolName</span>, string <span style="color:greenYellow">child_name</span>, int <span style="color:#FFF380">child_age</span>, int <span style="color:#FFF380">child_height</span>, int <span style="color:#FFF380">child_weight</span>, MultipartFile <span style="color:#FFF380">child_profile</span>)_**

    - ```mysql
      INSERT INTO Child(child_id, child_parentId, child_area, child_schoolName, child_name, child_age, child_height, child_weight, child_profile, child_BMI)
      VALUES (
        (SELECT CONCAT('child-', (
          												SELECT LPAD(COUNT(*) + 1, 4, '0')
                                  FROM Child
                                 )
                      )
        ),
        {child_parentId},
        {child_area},
        {child_schoolName},
        {child_name},
        {child_age},
        {child_height},
        {child_weight},
        {child_profile},
        ({child_weight} / ({child_height} * {child_height}))
      )
      ```

  - **_getMyChildren(string <span style="color:#38ACEC">*child_parentId*</span>)_**

    - ```mysql
      SELECT child_id, child_name, child_profile 
      FROM Child 
      WHERE child_parentId = {child_parentId}
      ```

    - 선택한 아이 **_child_id_** Local Storage에 저장

    - <span style="color:#9E7BFF">**_Return <List> [child_id, child_name, child_profile]_**</span>

  - **_getMyChild(string <span style="color:greenYellow">child_id</span>)_**
    
    - <span style="color:#9E7BFF">**_Return <ChildDto>_**</span>
    
  - **_removeChild(string <span style="color:greenYellow">child_id</span>)_**

  - **_modifyChild(string <span style="color:greenYellow">child_area</span>, string <span style="color:greenYellow">child_schoolName</span>, string <span style="color:greenYellow">child_name</span>, int <span style="color:#FFF380">child_age</span>, int <span style="color:#FFF380">child_height</span>, int <span style="color:#FFF380">child_weight</span>, MultipartFile <span style="color:#FFF380">child_profile</span>)_**

  - **_updatePreferenceForChild(string <span style="color:#38ACEC">preference_foodIngredientId</span>, string <span style="color:#38ACEC">preference_childId</span>)_**

    

- ### 냉장고 관리 [RefrigeratorController]

  - **_getMyRefrigeratorId(string <span style="color:#38ACEC">refrigerator_parentsId</span>)_**
    - <span style="color:#9E7BFF">**_Return <String> refrigerator_id_**</span>
  - **_getMyRefrigerator(string <span style="color:#38ACEC">refrigeratorDetail_refrigeratorId</span>)_**
    - <span style="color:#9E7BFF">**_Return <List> [refrigeratorDetail_ingredientName]_**</span>
  - **_addMyRefrigerator(string <span style="color:#38ACEC">refrigeratorDetail_refrigeratorId</span>, string <span style="color:greenYellow">refrigeratorDetail_ingredientName</span>)_**
  - **_removeMyRefrigerator(string <span style="color:#38ACEC">refrigeratorDetail_refrigeratorId</span>, string <span style="color:greenYellow">refrigeratorDetail_ingredientName</span>)_**

  

- ### 알러지 관리 [AllergyController]

  - **_getMyChildAllergyId(string <span style="color:#38ACEC">*allergy_childId*</span>)_**
    - **_<span style="color:#9E7BFF">Return <String> allergy_id</span>_**
  - **_getMyChildAllergy(string <span style="color:#38ACEC">*allergyDetail_allergyId*</span>)_**
    - **_<span style="color:#9E7BFF">Return <List> [allergyDetail_allergyName]</span>_**
  - **_addMyChildAllergy(string <span style="color:#38ACEC">allergyDetail_allergyId</span>, string <span style="color:greenYellow">allergyDetail_allergyName</span>)_**
  - **_removeMyChildAllergy(string <span style="color:#38ACEC">allergyDetail_allergyId</span>, string <span style="color:greenYellow">allergyDetail_allergyName</span>)_**

  

- ### 레시피 관리 [RecipeController]

  - **_getRecipeInfoId(string <span style="color:greenYellow">recipe_id</span>)_**

    - **_<span style="color:#9E7BFF">Return <string> recipeInfo_id</span>_**

  - **_getRecipeIngredientId(string <span style="color:greenYellow">recipe_id</span>)_**

    - **_<span style="color:#9E7BFF">Return <string> recipeIngredient_id</span>_**

  - **_getRecipeProcessId(string <span style="color:greenYellow">recipe_id</span>)_**

    - **_<span style="color:#9E7BFF">Return <string> recipeProcess_id</span>_**

  - **_getRecipeInfo(string <span style="color:greenYellow">recipeInfo_id</span>)_**

    - **_<span style="color:#9E7BFF">Return <RecipeInfoDto></span>_**

  - **_getRecipeIngredient(string <span style="color:greenYellow">recipeIngredient_id</span>)_**

    - **_<span style="color:#9E7BFF">Return <RecipeIngredientDto></span>_**

  - **_getRecipeProcess(string <span style="color:greenYellow">recipeProcess_id</span>)_**

    - **_<span style="color:#9E7BFF">Return <RecipeProcessDto></span>_**

  - **_searchByRecipeName(string <span style="color:#F75D59">input_recipeInfo_name</span>)_**

    - ```mysql
      SELECT * 
      FROM Recipe
      WHERE recipe_name LIKE "%{input_recipeName}%"
      ```

    - **_<span style="color:#9E7BFF">Return <String> recipe_id</span>_**

  - **_searchByRecipeIngredient(string <span style="color:#F75D59">input_recipeIngredient_name</span>)_**

  - **_giveDifficultyToRecipe(string <span style="color:#38ACEC">*recipeDifficulty_parentId*</span>, string <span style="color:#38ACEC">*recipeDifficulty_recipeId*</span>, string <span style="color:greenYellow">*recipeDifficulty_difficulty*</span>)_**

  - **_giveScopeToRecipe(string <span style="color:#38ACEC">*recipeDifficulty_parentId*</span>, string <span style="color:#38ACEC">*recipeDifficulty_recipeId*</span>, string <span style="color:greenYellow">*recipeDifficulty_scope*</span>)_**

  - **_scrapToRecipe(string <span style="color:#38ACEC">scrap_childId</span>, string <span style="color:#38ACEC">scrap_recipeId</span>)_**

  

- ### 식단기록 관리 [FoodRecordController]

  - **_finishRecipe(string <span style="color:#38ACEC">scrap_childId</span>, string <span style="color:#38ACEC">scrap_recipeId</span>, MultipartFile <span style="color:#FFF380">foodRecord_media</span>)_**
    - date : now()
    - foodRecord_media : 사용자가 찍은 음식 사진 없는 경우 레시피의 썸네일으로
  - **_getMyMonthlyFoodRecord(string <span style="color:#38ACEC">scrap_childId</span>, string <span style="color:#38ACEC">scrap_recipeId</span>, MultipartFile <span style="color:#FFF380">foodRecord_media</span>)_**
    - **_<span style="color:#9E7BFF">Return <List> [FoodRecordDto]</span>_**
  - **_removeMyFoodRecord(string <span style="color:#38ACEC">foodRecord_recipeId</span>)_**

  

- ### 메세지 관리 [MessageController]

  - **_sendMessage(string <span style="color:greenYellow">message_senderId</span>, string <span style="color:greenYellow">message_receiverId</span>, string <span style="color:greenYellow">message_content</span>)_**

  - **_getMyMessage(string <span style="color:greenYellow">message_senderId</span>, string <span style="color:greenYellow">message_receiverId</span>)_**

    - **_<span style="color:#9E7BFF">Return <List> [MessageDto]</span>_**

  - **_checkReadMessage(string <span style="color:greenYellow">message_id</span>, string <span style="color:greenYellow">message_flag</span>)_**

    

- ### 관리자 관리 [AdminController]

  - **_getAllParent()_**

    - **_<span style="color:#9E7BFF">Return <List> [parentDto]</span>_**

  - **_getParenInfo(string <span style="color:greenYellow">parent_id</span>)_**

    - **_<span style="color:#9E7BFF">Return <ParentDto></span>_**

  - **_getChildInfo(string <span style="color:greenYellow">child_id</span>, string <span style="color:#38ACEC">child_parentId</span>)_**

    - **_<span style="color:#9E7BFF">Return <ChildDto> </span>_**

  - **_addTip(string <span style="color:greenYellow">tip_title</span>, string <span style="color:greenYellow">tip_content</span>, string <span style="color:greenYellow">tip_media</span>)_**

  - **_modifyTip(string <span style="color:greenYellow">tip_id</span>, string <span style="color:greenYellow">tip_title</span>, string <span style="color:greenYellow">tip_content</span>, string <span style="color:greenYellow">tip_media</span>)_**

  - **_removeTip(string <span style="color:greenYellow">tip_id</span>)_**

  - **_getAllTip()_**

    - **_<span style="color:#9E7BFF">Return <List> [TipDto] </span>_**

  - **_getTipInfo(string <span style="color:greenYellow">tip_id</span>)_**

    - **_<span style="color:#9E7BFF">Return <TipDto> </span>_**

    

---

### Description

- <span style="color:greenYellow">**_required_**</span>
- <span style="color:#FFF380">**_not required_**</span>
- <span style="color:#38ACEC">**_foreign key_**</span>
- <span style="color:#F75D59">**_input_value_**</span>

---

### Development Naming Rule

- **_Camel Case_**
  - *variable*
- **_Pascal Case_**
  - *Package*
  - *Class*
- **_Snake Case_**
  - *Table*

---

### SUMMARY

- ##### **_21/03/11_**

  - ~~레시피 난이도 / 점수 insert, update~~
  - ~~레시피 검색 시 자동완성 front~~
  - ~~레시피 영양정보 DB 관련 문제~~
  - 아이 [지역 -> 학교명] 에서 지역 코드 _backend_ vs _frontend_
  
  
  
- ##### *_21/03/13_*

  - 급식 DB X
  - 학사 일정 확인 기능 추가
  - Parent 정보 추가
  - 급식 영양정보 제외하고 당일 영양정보 계산 기능 추가
  - 채팅 기능 추가

- ##### *_21/_*03/15

  - DB Table insert id 생성 count query문 수정

