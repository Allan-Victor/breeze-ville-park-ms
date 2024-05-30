package com.breezevillepark_ms.bvp_ms.recipe;

import com.breezevillepark_ms.bvp_ms.common.PageResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeMaterialService {
    private final RecipeMaterialRepository rmRepo;
    private final RecipeMaterialMapper recipeMaterialMapper;

    public Integer addRecipeMaterial(RecipeMaterialRequest recipeMaterialRequest) {

        RecipeMaterial rmMaterial = recipeMaterialMapper.toRecipeMaterial(recipeMaterialRequest);
        return rmRepo.save(rmMaterial).getRecipeMaterialId();
    }

    public PageResponse<RecipeMaterialResponse> findAllRecipeMaterials(int page, int size) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("submittedAt").descending());
        Page<RecipeMaterial> recipeMaterial = rmRepo.findAll(pageable);
        List<RecipeMaterialResponse> responses = recipeMaterial.stream()
                .map(recipeMaterialMapper::toRecipeMaterialResponse)
                .toList();
        return new PageResponse<>(
                responses,
                recipeMaterial.getNumber(),
                recipeMaterial.getSize(),
                recipeMaterial.getTotalElements(),
                recipeMaterial.getTotalPages(),
                recipeMaterial.isFirst(),
                recipeMaterial.isLast()
        );

    }

    public Integer updateRecipeMaterial(RecipeMaterialUpdateRequest recipeMaterialUpdateRequest) {
        RecipeMaterial recipeMaterial = recipeMaterialMapper.backtoRecipeMaterial(recipeMaterialUpdateRequest);
        return rmRepo.save(recipeMaterial).getRecipeMaterialId();
    }

    public void removeRecipeMaterialById(Integer recipeMaterialId) {
        if (!rmRepo.existsById(recipeMaterialId)){
            throw new EntityNotFoundException("The recipe does not exist");
        }
        rmRepo.deleteById(recipeMaterialId);
    }
}
