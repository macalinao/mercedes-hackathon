#!/usr/bin/env python

import Leap, time, logging ,IPython
from firebase import firebase

class SendGestures:

    finger_names = ['Thumb', 'Index', 'Middle', 'Ring', 'Pinky']
    zoom_flag = False
    zoom_origin = 0

    def __init__(self):
        self.init_logger()
        self.init_controller_settings()

        self.firebase = firebase.FirebaseApplication("https://benz.firebaseio.com/",None)
        self.user_name = 'gesture'

        data = {'swipe_right': True, 
                'swipe_left' : False,
                'rotate_clockwise': False,
                'rotate_counterclockwise' : False,
                'zoom_in' : False,
                'zoom_out': False,
                'tap': False,
                }

        snapshot = self.firebase.put('/leapdata',self.user_name ,data)
        print "should have sent the snapshot"
        #self.get_gesture_loop()

    def init_controller_settings(self):
        self.controller = Leap.Controller()

        self.controller.enable_gesture(Leap.Gesture.TYPE_CIRCLE)
        self.controller.enable_gesture(Leap.Gesture.TYPE_SCREEN_TAP)
        self.controller.enable_gesture(Leap.Gesture.TYPE_KEY_TAP)
        self.controller.enable_gesture(Leap.Gesture.TYPE_SWIPE)

        self.controller.config.set("Gesture.Circle.MinRadius", 10.0)
        self.controller.config.set("Gesture.Circle.MinArc", 1)
        self.controller.config.save()

    def init_logger(self):
        """setting up the logger"""
        self.log = logging.getLogger("gestures_sent_log")
        hdlr =logging.FileHandler('gestures_sent.log')
        formatter = logging.Formatter('%(asctime)s - %(funcName)s- %(message)s')
        hdlr.setFormatter(formatter)
        self.log.addHandler(hdlr) 
        self.log.setLevel(logging.INFO)


    def get_gesture_loop(self):
        self.log.info("started gesture loop")

        while True:
            self.frame = self.controller.frame()
            # print "Frame", self.frame.id

            gesture_list = self.frame.gestures()
            hand_list = self.frame.hands

            # Built-in gestures
            for gesture in gesture_list:

                # Circle gesture
                if gesture.type == gesture.TYPE_CIRCLE:
                    circle = Leap.CircleGesture(gesture)
                    if (circle.pointable.direction.angle_to(circle.normal) <= Leap.PI/2):
                        clockwiseness = "clockwise"
                    else:
                        clockwiseness = "counterclockwise"  
                    print clockwiseness

                # Swipe gesture
                if gesture.type == gesture.TYPE_SWIPE:
                    swipe = Leap.SwipeGesture(gesture)
                    if swipe.direction[0] > 0:
                        print "right swipe"
                    else:
                        print "left swipe"

            # Custom gestures
            for hand in hand_list:
                if hand.is_left: continue

                # Get fingers (only visible ones)
                finger_list = hand.fingers.extended()

                # Check if the zoom gesture exists in the current frame
                if len(finger_list)==2 and finger_list[0].type==0 and finger_list[1].type==1:

                    index_finger = finger_list[1]

                    # If flag is false, start the zooming gesture
                    # Record the current position as the starting point for zooming
                    if self.zoom_flag == False:
                        self.zoom_flag = True
                        self.zoom_origin = index_finger.tip_position[1]

                    # If the flag is true, it means that zooming gesture is 
                    # already active
                    else:
                        zoom_movement = index_finger.tip_position[1] - self.zoom_origin
                        print "zoom amount:", zoom_movement

                # If there's no zoom gesture in the view, set the flag back to false
                else:
                    self.zoom_flag = False


            time.sleep(0.1)


if __name__=="__main__":
    gesture = SendGestures()
