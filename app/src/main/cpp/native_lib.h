#ifndef NATIVE_LIB_H
#define NATIVE_LIB_H

#ifdef __cplusplus
extern "C" {
#endif

int testInt(int a, int b);

void testArrayByVal(const int *array, int len);
void testArrayByRef(int *array, int len);

void testStringByVal(const char *str);
void testStringByRef(char *str, int len);

typedef struct {
    char *str;
    int buffLen;
} MyString;
void testStructByVal(const MyString *myStr);
void testStructByRef(MyString *myStr);

typedef int (*SumCallback)(int a, int b);
int testCallBack(int a, int b, SumCallback sum);

typedef union {
    int field1;
    double field2;
} MyUnion;
void testUnionByVal(const MyUnion *myUnion, int fieldNum);
void testUnionByRef(MyUnion *myUnion, int fieldNum);

#ifdef __cplusplus
}
#endif

#endif //NATIVE_LIB_H
