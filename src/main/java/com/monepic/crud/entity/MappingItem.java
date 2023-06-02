package com.monepic.crud.entity;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.monepic.crud.api.ValidationController;
import com.monepic.crud.schema.annotation.JsonSchemaMetadata;
import com.monepic.crud.schema.annotation.ValidationUrl;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@JsonSchemaMetadata(key="classData", value="theClassData")
@JsonClassDescription("This is the MappingItem description")
public class MappingItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonSchemaMetadata(key = "someField", value="someValue")
    @Column(name="dfrom")
    private String from;

    @JsonPropertyDescription("Description of the to field")
    @Column(name="dto")
    private String to;


    @Email
    @ValidationUrl(controller = ValidationController.class, path = "email", parameters="email")
    private String email;

    @Size(min = 2, max = 30)
    public String getMyGetter() {
        return "hello";
    }

    @JsonProperty(access = Access.READ_ONLY)
    public double getReadOnlyThing() {
        return 12.5d;
    }

    @NotNull
    public String otherMethod() {
        return "should be ignored";
    }
}
