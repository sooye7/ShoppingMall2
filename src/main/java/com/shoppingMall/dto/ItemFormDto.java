package com.shoppingMall.dto;

import com.shoppingMall.constant.ItemSellStatus;
import com.shoppingMall.entity.Item;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ItemFormDto {
    private Long id;

    @NotBlank(message="상품명은 필수 입력 값입니다.")
    private String itemNm;

    @NotNull(message="가격은 필수 입력 값입니다.")
    private Integer price;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String itemDetail;

    @NotNull(message = "재고는 필수 입력 값입니다.")
    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    private static ModelMapper modelMapper=new ModelMapper();

    public Item createItem(){
        return modelMapper.map(this, Item.class);  // ItemFormDto -> Item 연결
    }
    public static ItemFormDto of(Item item){
        return modelMapper.map(item, ItemFormDto.class);  // Item -> ItemFormDto 연결
    }

}
