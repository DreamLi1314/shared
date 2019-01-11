#ifndef _LOGGER_H
#define _LOGGER_H

#include<stdio.h>
#include<string.h>

#define MAX_INFO_LENGTH 60

void throwWarning(char * msg) {
    char warning[MAX_INFO_LENGTH] = "------[Warning]: ";
    strcat(warning, msg);
    printf("%s\n", warning);
}

void throwError(char * msg) {
    char error[MAX_INFO_LENGTH] = "\n******[Error]: ";
    strcat(error, msg);
    perror(error);
    exit(EXIT_FAILURE);
}


#endif /* _LOGGER_H */
