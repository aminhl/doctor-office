package org.nexthope.doctoroffice.medicalrecord;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MedicalRecordMapper {

    MedicalRecordDTO toDto(MedicalRecord medicalRecord);

}
