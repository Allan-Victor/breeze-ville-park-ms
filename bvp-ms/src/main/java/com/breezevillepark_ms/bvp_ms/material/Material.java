package com.breezevillepark_ms.bvp_ms.material;

import com.breezevillepark_ms.bvp_ms.common.BaseEntity;
import com.breezevillepark_ms.bvp_ms.recipe.RecipeMaterial;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;



@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
public class Material extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "material_seq")
    @SequenceGenerator(name = "material_seq",
            sequenceName = "material_seq",
            allocationSize = 1)
    private Integer materialId;
    private String materialName;
    private String nameWithUnit;
    private Double quantity;
    private BigDecimal costPerUnit;
    private String unitOfMeasure;

    @OneToMany(mappedBy = "material")
    private List<RecipeMaterial> recipeMaterials;



    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }


    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public void setNameWithUnit(String nameWithUnit) {
        this.nameWithUnit = nameWithUnit;
    }



    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }



    public void setCostPerUnit(BigDecimal costPerUnit) {
        this.costPerUnit = costPerUnit;
    }


    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }



    public void setRecipeMaterials(List<RecipeMaterial> recipeMaterials) {
        this.recipeMaterials = recipeMaterials;
    }

    public String getNameWithUnit() {
        return this.materialName + " " + this.unitOfMeasure;
    }



}
