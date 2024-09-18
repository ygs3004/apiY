package kr.co.apiy.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Schema(name = "콤보박스 옵션")
@Getter
@Setter
@Builder
public class ComboResponse {

    @Schema(description = "콤보이름")
    private String name;

    @Schema(description = "콤보정보")
    @Builder.Default
    private List<ComboItem> comboList = new ArrayList<>();

    @Schema(name = "콤볻옵션")
    @Setter
    @Getter
    @Builder
    public static class ComboItem{
        @Schema(description = "표기")
        private String label;

        @Schema(description = "값")
        private String value;
    }

}
