import cv2
import numpy as np
import os

def ratio_counter(name):
    img = cv2.imread(name)
    white_pix = np.sum(img == 255)
    black_pix = np.sum(img == 0)
    total=white_pix+black_pix
    #print('Number of white pixels:', white_pix)
    #print('Number of black pixels:', black_pix)
    ratio=(white_pix/total)*100;
    return ratio

def tester(path):
    img = cv2.imread(os.path.join(path,'test.png'))
    edge = cv2.Canny(img,250, 400)
    cv2.imwrite(os.path.join(path,'cannytire.jpg'),edge)

    img = cv2.imread(os.path.join(path,'cannytire.jpg'))

    height = img.shape[0]
    width = img.shape[1]

    # Cut the image in half
    width_cutoff = width // 2
    s1 = img[:, :width_cutoff]
    s2 = img[:, width_cutoff:]

    cv2.imwrite(os.path.join(path,"chopped1.jpg"), s1)
    cv2.imwrite(os.path.join(path,"chopped2.jpg"), s2)

    ratio=ratio_counter(os.path.join(path,"cannytire.jpg"))
    #print('\nRatio of full image:',ratio)

    if(ratio>1):
            return "Good tire"

    else:
            return "Tire change Recommended"

def calibtest(path):

    img = cv2.imread(os.path.join(path,"chopped1.jpg"))
    leftwhitepx= np.sum(img == 255)
    #print('lft white px',leftwhitepx)

    img = cv2.imread(os.path.join(path,"chopped2.jpg"))
    rightwhitepx= np.sum(img == 255)
    #print('right white px', rightwhitepx)

    ratioLR=leftwhitepx-rightwhitepx
    #print('left(-) or right(+):',ratioLR)

    if(ratioLR>=2000):
        return "Possible Positive Camber"

    elif(ratioLR<=-2000):
        return "Possible Negative Camber"

    else:
        return "good calibration"

