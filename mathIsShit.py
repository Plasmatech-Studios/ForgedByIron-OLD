start = 0
end = 9999
doubles = 0
for i in range(start, end+1):
    myString = ""
    if i < 10:
        myString = "000" + str(i)
    elif i < 100:
        myString = "00" + str(i)
    elif i < 1000:
        myString = "0" + str(i)
    else:
        myString = str(i)
    
    if myString.count(str(0)) > 1:
        doubles += 1
    elif myString.count(str(1)) > 1:
        doubles += 1
    elif myString.count(str(2)) > 1:
        doubles += 1
    elif myString.count(str(3)) > 1:
        doubles += 1
    elif myString.count(str(4)) > 1:
        doubles += 1
    elif myString.count(str(5)) > 1:
        doubles += 1
    elif myString.count(str(6)) > 1:
        doubles += 1
    elif myString.count(str(7)) > 1:
        doubles += 1
    elif myString.count(str(8)) > 1:
        doubles += 1
    elif myString.count(str(9)) > 1:
        doubles += 1
    

print("doubles:", doubles)
print("probability:", round(doubles / (end - start), 4))