package de.eisingerf.elp.user.controller.dto;

import de.eisingerf.elp.common.api.rest.list.ListDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Slice;

@Schema(name = "UserList")
public class UserListDto extends ListDto<UserDto> {
    public UserListDto(Slice<UserDto> slice) {
        super(slice);
    }
}
