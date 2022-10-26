package br.com.taskregister.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class Issue {

    private Integer status;
    private LocalDateTime dateTime;
    private String title;
    private List<Field> fieldList;

    @Getter
    @Setter
    public static class Field {

        private String name;
        private String message;

        public Field(String name, String message) {
            super();
            this.name = name;
            this.message = message;
        }
    }
}
