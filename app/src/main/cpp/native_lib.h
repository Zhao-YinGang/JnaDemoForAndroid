#ifndef NATIVE_LIB_H
#define NATIVE_LIB_H

#ifdef __cplusplus
extern "C" {
#endif

int getInt(void);

void getString(char *str, size_t len);

const char * getString2();

struct User {
    char* name;
    int height;
    double weight;
};
void testUser(User user);
void testUserPointer(User *user);
void testUserPointer2(User *user);

#ifdef __cplusplus
}
#endif

#endif //NATIVE_LIB_H
