package com.sochic.sochic.Util.ApiMannager;

import com.sochic.sochic.BrandFolder.API.BrandAPI;
import com.sochic.sochic.BrandFolder.API.BrandInfoAPI;
import com.sochic.sochic.BrandFolder.API.BrandProfileAPI;
import com.sochic.sochic.HomeFolder.API.HomeItemAPI;
import com.sochic.sochic.LoginFolder.API.FindEmailAPI;
import com.sochic.sochic.LoginFolder.API.LoginAPI;
import com.sochic.sochic.LoginFolder.API.TermAPI;
import com.sochic.sochic.LoginFolder.API.UserInfoAPI;
import com.sochic.sochic.MyPageFolder.API.AddressAPI;
import com.sochic.sochic.MyPageFolder.API.AddressInfoAPI;
import com.sochic.sochic.MyPageFolder.API.CartAPI;
import com.sochic.sochic.MyPageFolder.API.CouponAPI;
import com.sochic.sochic.MyPageFolder.API.FollowingAlarmAPI;
import com.sochic.sochic.MyPageFolder.API.InviteAPI;
import com.sochic.sochic.MyPageFolder.API.MyAlarmAPI;
import com.sochic.sochic.MyPageFolder.API.MyFollowingAPI;
import com.sochic.sochic.MyPageFolder.API.MyPointAPI;
import com.sochic.sochic.MyPageFolder.API.MyProductCntAPI;
import com.sochic.sochic.MyPageFolder.API.MypageAPI;
import com.sochic.sochic.MyPageFolder.API.PointAPI;
import com.sochic.sochic.MyPageFolder.API.ProfileLoadAPI;
import com.sochic.sochic.MyPageFolder.API.ReviewAPI;
import com.sochic.sochic.OrderFolder.API.CancelReasonAPI;
import com.sochic.sochic.OrderFolder.API.ExchangeReasonAPI;
import com.sochic.sochic.OrderFolder.API.OrderAPI;
import com.sochic.sochic.OrderFolder.API.OrderCancelInfoAPI;
import com.sochic.sochic.OrderFolder.API.OrderDetailAPI;
import com.sochic.sochic.OrderFolder.API.OrderExchangeInfoAPI;
import com.sochic.sochic.OrderFolder.API.OrderExchangePriceAPI;
import com.sochic.sochic.OrderFolder.API.OrderReturnInfoView;
import com.sochic.sochic.OrderFolder.API.OrderShortInfoAPI;
import com.sochic.sochic.OrderFolder.API.OrderStateAPI;
import com.sochic.sochic.OrderFolder.API.ReturnReasonAPI;
import com.sochic.sochic.PayFolder.API.DeliveryMemoAPI;
import com.sochic.sochic.PayFolder.API.MyCouponAPI;
import com.sochic.sochic.PayFolder.API.OrderTempDeliveryAPI;
import com.sochic.sochic.PayFolder.API.OrderTempInfoAPI;
import com.sochic.sochic.PayFolder.API.OrderTempSavePointAPI;
import com.sochic.sochic.ProductFolder.API.OptionCountCheckAPI;
import com.sochic.sochic.ProductFolder.API.OptionItemAPI;
import com.sochic.sochic.ProductFolder.API.OptionZeroAPI;
import com.sochic.sochic.ProductFolder.API.OrderTempAPI;
import com.sochic.sochic.PayFolder.API.OrderTempCheckAPI;
import com.sochic.sochic.ProductFolder.API.ProductContactAPI;
import com.sochic.sochic.ProductFolder.API.ProductDeliveryAPI;
import com.sochic.sochic.ProductFolder.API.ProductDetailAPI;
import com.sochic.sochic.ProductFolder.API.ProductInfoCommentAPI;
import com.sochic.sochic.ProductFolder.API.ProductInfoNoticeAPI;
import com.sochic.sochic.ProductFolder.API.ProductPhotoAPI;
import com.sochic.sochic.ProductFolder.API.ProductReviewAPI;
import com.sochic.sochic.ProductFolder.API.ProductReviewPhotoAPI;
import com.sochic.sochic.SearchFolder.API.SearchTagAPI;
import com.sochic.sochic.SearchFolder.API.SubCategoryAPI;
import com.sochic.sochic.SearchFolder.API.SubCategorySearchAPI;
import com.sochic.sochic.SellerFolder.API.SellerCalcurateAPI;
import com.sochic.sochic.SellerFolder.API.SellerCalcurateDetailAPI;
import com.sochic.sochic.SellerFolder.API.SellerCodiItemAPI;
import com.sochic.sochic.SellerFolder.API.SellerContactDetailAPI;
import com.sochic.sochic.SellerFolder.API.SellerReDeliveryAPI;
import com.sochic.sochic.SellerFolder.API.SellerStateInfoAPI;
import com.sochic.sochic.SellerFolder.API.SellerStatisticAPI;
import com.sochic.sochic.SettingFolder.API.ContactAPI;
import com.sochic.sochic.SettingFolder.API.FaqAPI;
import com.sochic.sochic.SettingFolder.API.NoticeAPI;
import com.sochic.sochic.SettingFolder.API.TermUrlAPI;
import com.sochic.sochic.SplashFolder.API.GuideAPI;
import com.sochic.sochic.SplashFolder.API.PopupAPI;
import com.sochic.sochic.SplashFolder.API.SplashAPI;

import java.util.ArrayList;

import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public abstract interface ApiService
{


    //팝업
    @POST("app/app_popup")
    Single<PopupAPI> POPUP_API();

    //가이드
    @POST("app/app_guide")
    Single<GuideAPI> GUIDE_API();



    //이용 약관
    @POST("app/app_terms")
    Single<TermAPI> TERM_API();


    //이메일 중복체크
    @FormUrlEncoded
    @POST("member/email_overlap_check")
    Single<TrueFalseAPI> EMAIL_CHECK_API(@Field("email") String email);

    //이메일 회원가입
    @FormUrlEncoded
    @POST("member/user_register")
    Single<LoginAPI> REGISTER_API(@Field("email") String email,
                                  @Field("password") String password,
                                  @Field("rec_code") String rec_code,
                                  @Field("market_value") String market_value,
                                  @Field("os") String os,
                                  @Field("token") String token);



    //이메일 추가정보
    @FormUrlEncoded
    @POST("member/user_info_add")
    Single<TrueFalseAPI> ADD_INFO_API(@Field("id_user") String id_user,
                                      @Field("birthday") String birthday,
                                      @Field("interest[]")ArrayList<String> interest);

    //이메일 로그인
    @FormUrlEncoded
    @POST("member/user_login")
    Single<LoginAPI> LOGIN_API(@Field("email") String email,
                               @Field("password") String password,
                               @Field("os") String os,
                               @Field("token") String token);


    //회원 정보
    @FormUrlEncoded
    @POST("member/user_info")
    Single<UserInfoAPI> USER_INFO_API(@Field("id_user") String id_user);

    //OPEN ㄹㅣ스트
    @FormUrlEncoded
    @POST("home/open")
    Single<HomeItemAPI> OPEN_ITEM_API(@Field("id_user") String id_user);

    //NEW 리스트
    @FormUrlEncoded
    @POST("home/new")
    Single<HomeItemAPI> NEW_ITEM_API(@Field("id_user") String id_user);

    //BEST 리스트
    @FormUrlEncoded
    @POST("home/best")
    Single<HomeItemAPI> BEST_ITEM_API(@Field("id_user") String id_user);

    //FOLLOW 리스트
    @FormUrlEncoded
    @POST("home/follow")
    Single<HomeItemAPI> FOLLOW_ITEM_API(@Field("id_user") String id_user);

    //상품 좋아요 컨트롤
    @FormUrlEncoded
    @POST("home_action/goods_heart_controller")
    Single<LikeAPI> LIKE_API(@Field("id_user") String id_user, @Field("idx") String idx);

    //북마크 컨트롤
    @FormUrlEncoded
    @POST("home_action/goods_bookmark_controller")
    Single<LikeAPI> BOOKMARK_API(@Field("id_user") String id_user, @Field("idx") String idx);

    //브랜드 리스트
    @FormUrlEncoded
    @POST("brand/brand_list")
    Single<BrandAPI> BRAND_API(@Field("id_user") String id_user);

    //팔로우 컨트롤러
    @FormUrlEncoded
    @POST("home_action/follow_controller")
    Single<TrueFalseAPI> FOLLOW_API(@Field("id_user") String id_user, @Field("other_user") String other_user);

    //서브 카테고리 리스트
    @FormUrlEncoded
    @POST("search/sub_category_list")
    Single<SubCategoryAPI> SUB_CATEGORY_API(@Field("category_idx") String category_idx);


    //서브 카테고리 검색
    @FormUrlEncoded
    @POST("search/goods_category_filter_list")
    Single<SubCategorySearchAPI> SUB_CATEGORY_SEARCH_API(@Field("category_idx") String category_idx,
                                                         @Field("sub_category_idx") String sub_category_idx);

    //마이페이지 정보
    @FormUrlEncoded
    @POST("mypage/mypage_info")
    Single<MypageAPI> MYPAGE_API(@Field("id_user") String id_user);

    //상품 디테일
    @FormUrlEncoded
    @POST("goods/goods_info")
    Single<ProductDetailAPI> PRODUCT_DETAIL_API(@Field("id_user") String id_user, @Field("idx") String idx);

    //상품 설명 및 코멘트
    @FormUrlEncoded
    @POST("goods/goods_comment")
    Single<ProductInfoCommentAPI> PRODUCT_INFO_COMMENT_API(@Field("idx") String idx);

    //상품 사진
    @FormUrlEncoded
    @POST("goods/goods_info_photo_image")
    Single<ProductPhotoAPI> PRODUCT_PHOTO_API(@Field("idx") String idx);

    //상품 리뷰 사진
    @FormUrlEncoded
    @POST("goods/review_image_list")
    Single<ProductReviewPhotoAPI> PRODUCT_REVIEW_PHOTO_API(@Field("idx") String idx);

    //상품 리뷰 맥시멈5
    @FormUrlEncoded
    @POST("goods/review_new_list")
    Single<ProductReviewAPI> PRODUCT_REVIEW_MAX_API(@Field("idx") String idx);

    //상품 리뷰 리스트
    @FormUrlEncoded
    @POST("goods/review_list")
    Single<ProductReviewAPI> PRODUCT_REVIEW_API(@Field("idx") String idx);

    //상품 정보 고시
    @FormUrlEncoded
    @POST("goods/goods_info_noti")
    Single<ProductInfoNoticeAPI> PRODUCT_INFO_NOTICE_API(@Field("idx") String idx);

    //상품 Q&A리스트
    @FormUrlEncoded
    @POST("goods/goods_qna_list")
    Single<ProductContactAPI> PRODUCT_CONTACT_API(@Field("idx") String idx);

    //상품 배송정보
    @FormUrlEncoded
    @POST("goods/goods_delivery_info")
    Single<ProductDeliveryAPI> PRODUCT_DELIVERY_API(@Field("idx") String idx);

    //상품 문의하기
    @FormUrlEncoded
    @POST("goods/goods_qna_write")
    Single<TrueFalseAPI> PRODUCT_CONTACT_WRITE_API(@Field("idx") String idx,
                                                   @Field("id_user") String id_user,
                                                   @Field("title") String title,
                                                   @Field("contents") String contents);

    //쿠폰 유무 확인
    @FormUrlEncoded
    @POST("goods/goods_coupon_check")
    Single<TrueFalseAPI> PRODUCT_COUPON_CHECK_API(@Field("idx") String idx);

    //상품 코디 아이템
    @FormUrlEncoded
    @POST("goods/cody_item_list")
    Single<HomeItemAPI> CODI_ITEM_API(@Field("id_user") String id_user, @Field("seller_id") String seller_id, @Field("idx") String idx);

    //옵션 갯수 확인
    @FormUrlEncoded
    @POST("order_process/option_check")
    Single<OptionCountCheckAPI> OPTION_COUNT_CHECK_API(@Field("idx") String idx);

    //옵션이 없는 상품
    @FormUrlEncoded
    @POST("order_process/none_option")
    Single<OptionZeroAPI> OPTION_ZERO_API(@Field("idx") String idx);

    //옵션 1개인 상품 내용
    @FormUrlEncoded
    @POST("order_process/one_option")
    Single<OptionItemAPI> OPTION_SINGLE_API(@Field("idx") String idx);

    //옵션 2개인 상품 내용
    @FormUrlEncoded
    @POST("order_process/two_option")
    Single<OptionItemAPI> OPTION_Multi_API(@Field("idx") String idx, @Field("o_idx") String o_idx);


    //옵션 없을 경우 템프 쌓기
    @FormUrlEncoded
    @POST("order_process/order_temp_none_option")
    Single<OrderTempAPI> ORDER_NONE_TEMP_API(@Field("idx") String idx, @Field("id_user") String id_user, @Field("o_idx") String o_idx, @Field("o_cnt") String o_cnt, @Field("o_price") String o_price);


    //옵션이 한개일 경우 템프 쌓기
    @FormUrlEncoded
    @POST("order_process/order_temp_one_option")
    Single<OrderTempAPI> ORDER_SINGLE_TEMP_API(@Field("idx") String idx,
                                               @Field("id_user") String id_user,
                                               @Field("o_idx_list[]") ArrayList<String> o_idx_list,
                                               @Field("o_cnt_list[]") ArrayList<String> o_cnt_list,
                                               @Field("o_price[]") ArrayList<String> o_price);

    //옵션이 여러개일 경우 템프 쌓기
    @FormUrlEncoded
    @POST("order_process/order_temp_two_option")
    Single<OrderTempAPI> ORDER_MULTI_TEMP_API(@Field("idx") String idx,
                                               @Field("id_user") String id_user,
                                               @Field("o_idx_list[]") ArrayList<String> o_idx_list,
                                               @Field("sub_index_list[]") ArrayList<String> sub_index_list,
                                               @Field("o_cnt_list[]") ArrayList<String> o_cnt_list,
                                               @Field("o_price[]") ArrayList<String> o_price);

    //오더 템프 카운트 체크
    @FormUrlEncoded
    @POST("order_process/order_temp_goods_cnt_check")
    Single<OrderTempCheckAPI> ORDER_TEMP_CHECK_API(@Field("o_code") String o_code);

    //오더 템프 정보
    @FormUrlEncoded
    @POST("order_process/order_temp_goods_one_info")
    Single<OrderTempInfoAPI> ORDER_TEMP_SINGLE_INFO_API(@Field("o_code") String o_code);

    //오더 템프 여러개 일 경우 정보
    @FormUrlEncoded
    @POST("order_process/order_temp_goods_two_info")
    Single<OrderTempInfoAPI> ORDER_TEMP_MULTI_INFO_API(@Field("o_code") String o_code);

    //배송지 정보
    @FormUrlEncoded
    @POST("order_process/member_delivery_list")
    Single<AddressAPI> ADDRESS_API(@Field("id_user") String id_user);

    //배송 메모
    @POST("order_process/delivery_memo")
    Single<DeliveryMemoAPI> DELIVERY_MEMO_API();

    //배송 메모 선택
    @FormUrlEncoded
    @POST("order_process/delivery_selected_update")
    Single<TrueFalseAPI> ADDRESS_SELECT_API(@Field("id_user") String id_user, @Field("d_idx") String idx);

    //오더 템프 적립 혜택
    @FormUrlEncoded
    @POST("order_process/order_save_point")
    Single<OrderTempSavePointAPI> ORDER_TEMP_SAVE_POINT_API(@Field("o_code") String o_code);

    //배송지 추가하기
    @FormUrlEncoded
    @POST("order_process/delivery_address_add")
    Single<TrueFalseAPI> ADD_ADDRESS_API(@Field("id_user") String id_user,
                                         @Field("name") String name,
                                         @Field("phone") String phone,
                                         @Field("post_number") String post_number,
                                         @Field("address") String address,
                                         @Field("address_detail") String address_detail);

    //배송지 정보 불러오기
    @FormUrlEncoded
    @POST("order_process/delivery_address_info")
    Single<AddressInfoAPI> ADDRESS_INFO_API(@Field("d_idx") String d_idx);

    //배송지 수정하기
    @FormUrlEncoded
    @POST("order_process/delivery_address_update")
    Single<TrueFalseAPI>  MODIFY_ADDRESS_API(@Field("d_idx") String d_idx,
                                         @Field("name") String name,
                                         @Field("phone") String phone,
                                         @Field("post_number") String post_number,
                                         @Field("address") String address,
                                         @Field("address_detail") String address_detail);

    //배송지 삭제하기
    @FormUrlEncoded
    @POST("order_process/delivery_address_remove")
    Single<TrueFalseAPI> DELETE_ADDRESS_API(@Field("d_idx") String d_idx, @Field("id_user") String id_user);

    //내 보유 쿠폰 목록
    @FormUrlEncoded
    @POST("order_process/holding_coupon")
    Single<MyCouponAPI> MY_COUPON_API(@Field("id_user") String id_user, @Field("o_code") String o_code);

    //상품 쿠폰 목
    @FormUrlEncoded
    @POST("order_process/goods_cpn_list")
    Single<CouponAPI> PRODUCT_CPN_LIST_API(@Field("id_user") String id_user, @Field("idx") String idx);

    //배송비와 결제 약관
    @FormUrlEncoded
    @POST("order_process/delivery_price_terms_url")
    Single<OrderTempDeliveryAPI> ORDER_TEMP_DELIVERY_API(@Field("o_code") String o_code);

    //결제하기
    @FormUrlEncoded
    @POST("order_process/order_temp_pay")
    Single<TrueFalseAPI> ORDER_PAY_API(@Field("o_code") String o_code,
                                       @Field("paid_price") int paid_price,
                                       @Field("c_idx_list[]") ArrayList<String> c_idx_list,
                                       @Field("d_idx") String d_idx,
                                       @Field("used_point") int used_point,
                                       @Field("m_idx") String m_idx,
                                       @Field("o_name") String o_name,
                                       @Field("o_phone") String o_phone,
                                       @Field("o_email") String o_email,
                                       @Field("cpn_use_price") int cpn_use_price
    );

    //프로필 불러오기
    @FormUrlEncoded
    @POST("mypage/user_info")
    Single<ProfileLoadAPI> PROFILE_LOAD_API(@Field("id_user") String id_user);

    //프로필 사진 변경
    @POST("mypage/profile_image_update")
    Single<TrueFalseAPI> PROFILE_IMAGE_UPDATE_API(@Body RequestBody body);

    //프로필 정보 변경
    @FormUrlEncoded
    @POST("mypage/user_info_update")
    Single<TrueFalseAPI> PROFILE_INFO_MODIFY_API(@Field("id_user") String id_user,
                                                 @Field("email") String email,
                                                 @Field("birthday") String birthday,
                                                 @Field("interest[]") ArrayList<String> interest,
                                                 @Field("market_value") int market_value);

    //나의 포인트 정보
    @FormUrlEncoded
    @POST("mypage/my_point")
    Single<MyPointAPI> MY_POINT_API(@Field("id_user") String id_user);

    //나의 포인트 내역
    @FormUrlEncoded
    @POST("mypage/user_point_history")
    Single<PointAPI> MY_POINT_LIST_API(@Field("id_user") String id_user,
                                        @Field("duration") int type,
                                       @Field("type") int duration);

    //내 초대 정보
    @FormUrlEncoded
    @POST("mypage/user_recommend_info")
    Single<InviteAPI> INVITE_API(@Field("id_user") String id_user);

    //내 팔로잉 리스트
    @FormUrlEncoded
    @POST("mypage/following_list")
    Single<MyFollowingAPI> MY_FOLLOWING_API(@Field("id_user") String id_user);

    //전체 쿠폰 리스트
    @FormUrlEncoded
    @POST("order_process/coupon_all_list")
    Single<CouponAPI> COUPON_API(@Field("id_user") String id_user, @Field("duration") int duration);

    //다운 쿠폰 리스트
    @FormUrlEncoded
    @POST("order_process/member_down_coupon")
    Single<CouponAPI> DOWN_COUPON_API(@Field("id_user") String id_user, @Field("duration") int duration);

    //푸쉬 체크
    @FormUrlEncoded
    @POST("setting/user_push_check")
    Single<TrueFalseAPI> PUSH_CHECK_API(@Field("id_user") String id_user);

    //푸시 컨트롤러
    @FormUrlEncoded
    @POST("setting/push_controller")
    Single<TrueFalseAPI> PUSH_CON_API(@Field("id_user") String id_user);

    //서비스 이용약관
    @POST("setting/app_terms_1")
    Single<TermUrlAPI> SERVICE_TERM_API();

    //개인정보 이용약관
    @POST("setting/app_terms_2")
    Single<TermUrlAPI> PRIVATE_TERM_API();

    //공지 사항
    @POST("setting/app_notice")
    Single<NoticeAPI> NOTICE_API();

    //FAQ
    @FormUrlEncoded
    @POST("setting/app_faq")
    Single<FaqAPI> FAQ_API(@Field("id_user") String id_user);

    //브랜드 필터
    @FormUrlEncoded
    @POST("search/brand_list_filter")
    Single<BrandAPI> BRAND_FILTER_API(@Field("id_user") String id_user,
                                      @Field("quick") int quick,
                                      @Field("age_group[]") ArrayList<String> age_group,
                                      @Field("interest[]") ArrayList<String> interest);

    //브랜드 프로필 정보
    @FormUrlEncoded
    @POST("seller/seller_info")
    Single<BrandProfileAPI> BRAND_PROFILE_API(@Field("id_user") String id_user, @Field("seller_id") String seller_id);

    //브랜드 판매 상품
    @FormUrlEncoded
    @POST("seller/seller_products")
    Single<HomeItemAPI> BRAND_PRODUCT_ITEM_API(@Field("id_user") String id_user, @Field("seller_id") String seller_id);

    //브랜드 리뷰 리스트
    @FormUrlEncoded
    @POST("seller/seller_review")
    Single<ProductReviewAPI> BRAND_REVIEW_ITEM_API(@Field("id_user") String id_user, @Field("seller_id") String seller_id);

    //브랜드 부가 정보
    @FormUrlEncoded
    @POST("seller/seller_detail_info")
    Single<BrandInfoAPI> BRAND_INFO_API(@Field("id_user") String id_user, @Field("seller_id") String seller_id);

    //키워드 상품 검색
    @FormUrlEncoded
    @POST("search/search_products")
    Single<SubCategorySearchAPI> KEYWORD_SEARCH_PRODUCT_API(@Field("id_user") String id_user, @Field("keyword") String keyword);

    //키워드 브랜드 검색
    @FormUrlEncoded
    @POST("search/search_brand")
    Single<BrandAPI> KEYWORD_SEARCH_BRAND_API(@Field("id_user") String id_user, @Field("keyword") String keyword);

    //키워드 태그 1차검색
    @FormUrlEncoded
    @POST("search/tag_list")
    Single<SearchTagAPI> KEYWORD_SEARCH_TAG_FIRST_API(@Field("id_user") String id_user, @Field("keyword") String keyword);

    //키워드 태그 2차검색
    @FormUrlEncoded
    @POST("search/search_tag")
    Single<SubCategorySearchAPI> KEYWORD_SEARCH_TAG_SEC_API(@Field("id_user") String id_user, @Field("keyword") String keyword);

    //내 알림창
    @FormUrlEncoded
    @POST("notification/notification_history")
    Single<MyAlarmAPI> MY_ALARM_API(@Field("id_user") String id_user);

    //팔로잉 알림창
    @FormUrlEncoded
    @POST("notification/following_notification_history")
    Single<FollowingAlarmAPI> FOLLOWING_ALARM_API(@Field("id_user") String id_user);

    //회원 탈퇴
    @FormUrlEncoded
    @POST("setting/member_drop")
    Single<TrueFalseAPI> LEAVE_API(@Field("id_user") String id_user, @Field("reason") String reason);

    //문의 등돍
    @POST("setting/app_qna_write")
    Single<TrueFalseAPI> CONTACT_WRITE_API(@Body RequestBody body);

    //문의 내역
    @FormUrlEncoded
    @POST("setting/user_qna_list")
    Single<ContactAPI> CONTACT_API(@Field("id_user") String id_user);

    //주문/배송/조회
    @FormUrlEncoded
    @POST("order_view/order_list")
    Single<OrderAPI> ORDER_API(@Field("id_user") String id_user, @Field("duration") int duration, @Field("order_confirm") int order_confirm);

    //주문 상세
    @FormUrlEncoded
    @POST("order_view/order_detail")
    Single<OrderDetailAPI> ORDER_DETAIL_API(@Field("id_user") String id_user, @Field("o_code") String o_code, @Field("sub_o_code") String sub_o_code);

    //주문 상태 확인
    @FormUrlEncoded
    @POST("order_view/order_confirm_check")
    Single<OrderStateAPI> ORDER_STATE_API(@Field("o_code") String o_code, @Field("sub_o_code") String sub_o_code);

    //주문 정보 요약
    @FormUrlEncoded
    @POST("order_view/claim_apply_info_goods")
    Single<OrderShortInfoAPI> ORDER_SHORT_INFO_API(@Field("o_code") String o_code);

    //취소 사유
    @POST("order_view/claim_cancel_reason_list")
    Single<CancelReasonAPI> CANCEL_REASON_API();

    //주문 취소
    @FormUrlEncoded
    @POST("order_view/claim_cancel")
    Single<TrueFalseAPI> ORDER_CANCEL_API(@Field("id_user") String id_user,
                                          @Field("o_code") String o_code,
                                          @Field("sub_o_index[]") ArrayList<String> sub_o_index,
                                          @Field("canidx") String canidx);


    //반품 사유order_view/claim_return_reason_list
    @POST("order_view/claim_return_reason_list")
    Single<ReturnReasonAPI> RETURN_REASON_API();

    //교환 사유
    @POST("order_view/claim_exchange_reason_list")
    Single<ExchangeReasonAPI> EXCHANGE_REASON_API();

    //주문 교환
    @FormUrlEncoded
    @POST("order_view/claim_exchange")
    Single<TrueFalseAPI> ORDER_EXCHANGE_API(@Field("id_user") String id_user,
                                            @Field("o_code") String o_code,
                                            @Field("sub_o_index[]") ArrayList<String> sub_o_index,
                                            @Field("exidx") String exidx,
                                            @Field("post_number") String post_number,
                                            @Field("address") String address,
                                            @Field("address_detail") String address_detail,
                                            @Field("detail_info") String detail_info);

    //주문 반품
    @FormUrlEncoded
    @POST("order_view/claim_return")
    Single<TrueFalseAPI> ORDER_RETURN_API(@Field("id_user") String id_user,
                                          @Field("o_code") String o_code,
                                          @Field("sub_o_index[]") ArrayList<String> sub_o_index,
                                            @Field("reidx") String exidx,
                                          @Field("post_number") String post_number,
                                          @Field("address") String address,
                                            @Field("address_detail") String address_detail,
                                          @Field("detail_info") String detail_info,
                                          @Field("bank_name") String bank_name,
                                          @Field("bank_number") String bank_number );

    //교환 접수 정보

    @FormUrlEncoded
    @POST("order_view/exchange_info")
    Single<OrderExchangeInfoAPI> ORDER_EXCHANGE_INFO_API(@Field("o_code") String o_code, @Field("claim_code") String claim_code);

    //취소 접수 정보
    @FormUrlEncoded
    @POST("order_view/cancel_info")
    Single<OrderCancelInfoAPI> ORDER_CANCEL_INFO_API(@Field("o_code") String o_code, @Field("claim_code") String claim_code);

    //반품 접수 정보
    @FormUrlEncoded
    @POST("order_view/return_info")
    Single<OrderReturnInfoView> ORDER_RETURN_INFO_VIEW(@Field("o_code") String o_code, @Field("claim_code") String claim_code);

    //위시리스트
    @FormUrlEncoded
    @POST("order_view/bookmark_list")
    Single<SubCategorySearchAPI> MY_BOOKMARK_LIST_API(@Field("id_user") String id_user);

    //리뷰 리스트
    @FormUrlEncoded
    @POST("mypage/review_list")
    Single<ReviewAPI> REVIEW_API(@Field("id_user") String id_user, @Field("duration") String duration, @Field("state") String state);

    //샐러 체크
    @FormUrlEncoded
    @POST("seller/seller_check")
    Single<TrueFalseAPI> SELLER_CHECK_API(@Field("id_user") String id_user);

    //셀러 방문/매출/팔로우 수
    @FormUrlEncoded
    @POST("seller/seller_statistics")
    Single<SellerStatisticAPI> SELLER_STATISTIC_API(@Field("id_user") String id_user, @Field("type") int type);

    //판매자 페이지 현황
    @FormUrlEncoded
    @POST("seller/seller_state_info")
    Single<SellerStateInfoAPI> SELLER_STATE_INFO_API(@Field("id_user") String id_user);

    //판매자 상품 문의
    @FormUrlEncoded
    @POST("seller/seller_qna")
    Single<ContactAPI> SELLER_CONTACT_API(@Field("id_user") String id_user);

    //판매자 상품 문의 디테일
    @FormUrlEncoded
    @POST("seller/qna_detail")
    Single<SellerContactDetailAPI> SELLER_CONTACT_DETAIL_API(@Field("q_idx") String q_idx);

    //판매자 상품 문의 답변
    @FormUrlEncoded
    @POST("seller/qna_answer")
    Single<TrueFalseAPI> SELLER_CONTACT_ANSWER_API(@Field("q_idx") String q_idx, @Field("answer") String answer);

    //판매자 리뷰 관리
    @FormUrlEncoded
    @POST("seller/seller_goods_review_list")
    Single<ProductReviewAPI> SELLER_REVIEW_API(@Field("id_user") String id_user);

    //판매자 주문 내역
    @FormUrlEncoded
    @POST("seller/seller_order_list")
    Single<OrderAPI> SELLER_ORDER_LIST_API(@Field("id_user") String id_user, @Field("duration") int duration, @Field("type") int type);

    //회송 송장 여부체크
    @FormUrlEncoded
    @POST("seller/re_delivery_check")
    Single<TrueFalseAPI> SELLER_REDELIVERY_CHECK_API(@Field("id_user") String id_user, @Field("o_code") String o_code, @Field("claim_code") String claim_code);

    //회수 송장 번호 입력
    @FormUrlEncoded
    @POST("seller/re_delivery_info_add")
    Single<TrueFalseAPI> SELLER_REDELIVERY_ADD_API(@Field("claim_code") String claim_code,
                                                   @Field("name") String name,
                                                   @Field("number") String number);

    //회수 송장번호 불러오기
    @FormUrlEncoded
    @POST("seller/re_delivery_info")
    Single<SellerReDeliveryAPI> SELLER_RE_DELIVERY_API(@Field("id_user") String id_user, @Field("o_code") String o_code, @Field("claim_code") String claim_code);

    //교환 송장 여부체크
    @FormUrlEncoded
    @POST("seller/ex_delivery_check")
    Single<TrueFalseAPI> SELLER_EXCHANGE_CHECK_API(@Field("id_user") String id_user, @Field("o_code") String o_code, @Field("claim_code") String claim_code);

    //교환 송장 번호 입력
    @FormUrlEncoded
    @POST("seller/ex_delivery_info_add")
    Single<TrueFalseAPI> SELLER_EXCHANGE_ADD_API(@Field("claim_code") String claim_code, @Field("name") String name, @Field("number") String number);

    //교환 송장번호 불러오기
    @FormUrlEncoded
    @POST("seller/ex_delivery_info")
    Single<SellerReDeliveryAPI> SELLER_EXCHANGE_API(@Field("id_user") String id_user, @Field("o_code") String o_code, @Field("claim_code") String claim_code);

    //교환 완료 액션
    @FormUrlEncoded
    @POST("seller/exchange_complete_action")
    Single<TrueFalseAPI> SELLER_EX_COMPLETE_ACT_API(@Field("claim_code") String  claim_code);

    //송장 여부체크
    @FormUrlEncoded
    @POST("seller/delivery_check")
    Single<TrueFalseAPI> SELLER_NUMBER_CHECK_API(@Field("id_user") String id_user, @Field("o_code") String o_code, @Field("sub_o_code") String sub_o_code);

    //송장 번호 입력
    @FormUrlEncoded
    @POST("seller/delivery_info_add")
    Single<TrueFalseAPI> SELLER_NUMBER_ADD_API(@Field("sub_o_code") String sub_o_code,
                                               @Field("name") String name,
                                               @Field("number") String number);

    //송장번호 불러오기
    @FormUrlEncoded
    @POST("seller/delivery_info")
    Single<SellerReDeliveryAPI> SELLER_NUMBER_API(@Field("id_user") String id_user, @Field("o_code") String o_code, @Field("sub_o_code") String sub_o_code);

    //송장 완료 액션
    @FormUrlEncoded
    @POST("seller/delivery_ing_action")
    Single<TrueFalseAPI> SELLER_NUMBER_ACT_API(@Field("sub_o_code") String  sub_o_code);

    //반품 회수 송장 여부체크
    @FormUrlEncoded
    @POST("seller/re_ex_delivery_check")
    Single<TrueFalseAPI> SELLER_RETURN_CHECK_API(@Field("id_user") String id_user, @Field("o_code") String o_code, @Field("claim_code") String claim_code);

    //반품 회수 송장 번호 입력
    @FormUrlEncoded
    @POST("seller/re_ex_delivery_info_add")
    Single<TrueFalseAPI> SELLER_RETURN_ADD_API(@Field("claim_code") String claim_code, @Field("name") String name, @Field("number") String number);

    //반품 회수 송장번호 불러오기
    @FormUrlEncoded
    @POST("seller/re_ex_delivery_info")
    Single<SellerReDeliveryAPI> SELLER_RETURN_API(@Field("id_user") String id_user, @Field("o_code") String o_code, @Field("claim_code") String claim_code);

    //반품 완료 액션
    @FormUrlEncoded
    @POST("seller/return_complete_action")
    Single<TrueFalseAPI> SELLER_RETURN_ACT_API(@Field("claim_code") String  claim_code);

    //리뷰 정보 불러오기
    @FormUrlEncoded
    @POST("mypage/review_write_goods_info")
    Single<ReviewAPI> REVIEW_DETAIL_API(@Field("id_user") String id_user, @Field("sub_o_index") String sub_o_index);

    //문의 등돍
    @POST("mypage/review_write")
    Single<TrueFalseAPI> REVIEW_WRITE_API(@Body RequestBody body);

    //옵션 없는 상품 장바구니 담기
    @FormUrlEncoded
    @POST("order_process/cart_add_none_option")
    Single<TrueFalseAPI> NONE_ITEM_ADD_CART_API(@Field("idx") String idx,
                                                @Field("id_user") String id_user,
                                                @Field("o_idx") String o_idx,
                                                @Field("o_cnt") int cnt,
                                                @Field("o_price") int price);

    //옵션이 한개일 경우 장바구니 담기
    @FormUrlEncoded
    @POST("order_process/cart_add_one_option")
    Single<OrderTempAPI> SINGLE_ITEM_ADD_CART_API(@Field("idx") String idx,
                                               @Field("id_user") String id_user,
                                               @Field("o_idx_list[]") ArrayList<String> o_idx_list,
                                               @Field("o_cnt_list[]") ArrayList<String> o_cnt_list,
                                               @Field("o_price[]") ArrayList<String> o_price);

    //옵션이 여러개일 경우 장바구니 담기
    @FormUrlEncoded
    @POST("order_process/cart_add_two_option")
    Single<OrderTempAPI> MULTI_ITEM_ADD_CART_API(@Field("idx") String idx,
                                              @Field("id_user") String id_user,
                                              @Field("o_idx_list[]") ArrayList<String> o_idx_list,
                                              @Field("sub_index_list[]") ArrayList<String> sub_index_list,
                                              @Field("o_cnt_list[]") ArrayList<String> o_cnt_list,
                                              @Field("o_price[]") ArrayList<String> o_price);

    //나의 상품 카운트
    @FormUrlEncoded
    @POST("order_view/my_goods_state")
    Single<MyProductCntAPI> MY_PRODUCT_CNT_API(@Field("id_user") String id_user);

    //판매자 상품등록

    @POST("seller/store_goods_upload")
    Single<TrueFalseAPI> SELLER_PRODUCT_UPLOAD_API(@Body RequestBody body);


    //장바구니 목록
    @FormUrlEncoded
    @POST("order_view/cart_list")
    Single<CartAPI> CART_ITEM_API(@Field("id_user") String id_user);

    //장바구니 삭제
    @FormUrlEncoded
    @POST("order_view/cart_remove")
    Single<TrueFalseAPI> CART_REMOVE_API(@Field("id_user") String id_user, @Field("sub_o_index") String sub_o_index);

    //장바구니 결제
    @FormUrlEncoded
    @POST("order_process/cart_pay_temp")
    Single<OrderTempAPI> CART_TEMP_PAY_API(@Field("id_user") String id_user, @Field("seller_id_list[]") ArrayList<String> seller_id_list);

    //코디 아이템
    @FormUrlEncoded
    @POST("seller/seller_cody_add_goods_list")
    Single<SellerCodiItemAPI> SELLER_CODI_ITEM_API(@Field("id_user") String id_user, @Field("category_idx") String idx, @Field("sub_category_idx") String sub_category_idx);

    //교환 배송비 확인
    @FormUrlEncoded
    @POST("order_view/exchange_price")
    Single<OrderExchangePriceAPI> ORDER_EXCHANGE_PRICE_API(@Field("o_code") String o_code);

    //북마크 전체 삭제
    @FormUrlEncoded
    @POST("order_view/bookmark_all_remove")
    Single<TrueFalseAPI> BOOKMARK_CLEAR_API(@Field("id_user") String id_user);

    //장바구니 전체 삭제
    @FormUrlEncoded
    @POST("order_view/cart_all_remove")
    Single<TrueFalseAPI> CART_CLEAR_API(@Field("id_user") String id_user);

    //쿠폰 다운 로드
    @FormUrlEncoded
    @POST("mypage/coupon_down")
    Single<TrueFalseAPI> COUPON_DOWN_API(@Field("id_user") String id_user, @Field("c_idx") String c_idx);

    //장선내역
    @FormUrlEncoded
    @POST("seller/calculate_history")
    Single<SellerCalcurateAPI> SELLER_CALCURATE_API(@Field("id_user") String id_user, @Field("duration") int duration, @Field("type") int type);

    //정산 상세 내역
    @FormUrlEncoded
    @POST("seller/calculate_history_detail")
    Single<SellerCalcurateDetailAPI> SELLER_CALCURATE_DETAIL_API(@Field("id_user") String id_user, @Field("duration") int duration, @Field("type") int type);

    //아이디 찾기
    @FormUrlEncoded
    @POST("member/email_register_check")
    Single<FindEmailAPI> FIND_EMAIL_API(@Field("email") String emaiil);

    //비밀번호 찾기
    @FormUrlEncoded
    @POST("member/email_send")
    Single<TrueFalseAPI> SEND_PASS_EMAIL_API(@Field("email") String email, @Field("url") String url);

    //비밀번호 변경
    @FormUrlEncoded
    @POST("member/password_change")
    Single<TrueFalseAPI> PASS_CHANGE_API(@Field("id_user") String id_user, @Field("new_password") String new_password);

    //마케팅 url
    @POST("app/marketing_terms")
    Single<TermUrlAPI> MARKET_TERM_API();

    //카카오 로그인 체크
    @FormUrlEncoded
    @POST("member/kakao_login_check")
    Single<LoginAPI> KAKAO_CHECK_API(@Field("sns_id") String sns_id, @Field("os") String os, @Field("token") String token);

    //카카오 가입하기
    @FormUrlEncoded
    @POST("member/kakao_login")
    Single<LoginAPI> KAKAO_REGISTER_API(@Field("email") String email,
                                        @Field("sns_id") String sns_id,
                                        @Field("rec_code") String rec_code,
                                        @Field("market_value") String market_value,
                                        @Field("os") String os,
                                        @Field("token") String token);


    //카카오 로그인 체크
    @FormUrlEncoded
    @POST("member/naver_login_check")
    Single<LoginAPI> NAVER_CHECK_API(@Field("sns_id") String sns_id, @Field("os") String os, @Field("token") String token);

    //카카오 가입하기
    @FormUrlEncoded
    @POST("member/naver_login")
    Single<LoginAPI> NAVER_REGISTER_API(@Field("email") String email,
                                        @Field("sns_id") String sns_id,
                                        @Field("rec_code") String rec_code,
                                        @Field("market_value") String market_value,
                                        @Field("os") String os,
                                        @Field("token") String token);

    @POST("app/app_splash")
    Single<SplashAPI> SPLASH_API();

}