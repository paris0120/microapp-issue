package microapp.service.mapper;

import microapp.domain.IssueAssignment;
import microapp.service.dto.IssueAssignmentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link IssueAssignment} and its DTO {@link IssueAssignmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface IssueAssignmentMapper extends EntityMapper<IssueAssignmentDTO, IssueAssignment> {}
