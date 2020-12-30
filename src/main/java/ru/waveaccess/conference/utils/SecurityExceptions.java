package ru.waveaccess.conference.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SecurityExceptions {
    BAD_CREDENTIALS("bad credentials"),
    LOCKED("your account is locked, please contact moderators");
    String val;
}
