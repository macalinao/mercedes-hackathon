#!/usr/bin/env python

import Leap, time, logging ,IPython, datetime
from firebase import firebase

class SendGestures:
    def __init__(self):
        self.init_logger()
        self.init_controller_settings()
        self.firebase = firebase.FirebaseApplication("https://benz.firebaseio.com/",None)
        self.user_name = "leap_data"


        new_activity = {
            "user": "Ustun",
            "action_type": "asked",
            "text": "a text questions",
            "url": "a url"
        }


        #result = self.firebase.post("/activities", new_activity)
        data = {'swipe_right': True, 
                'swipe_left' : False,
                'rotate_clockwise': False,
                'rotate_counterclockwise' : False,
                'zoom_in' : False,
                'zoom_out': False,
                'tap': False,
                }

        #snapshot = self.firebase.post('/activity', data)
        snapshot = self.firebase.put('/leapdata',"gesture_info" ,data)
        #print(snapshot['name'])

        #print "result is ", result
        IPython.embed()
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

            gesture_list = self.frame.gestures()
            print "Number of gestures found ", len(gesture_list) 
            for i in range(len(gesture_list)):
                gesture = gesture_list[i]

                if gesture.type == gesture.TYPE_CIRCLE:
                    circle = Leap.CircleGesture(gesture)
                    if (circle.pointable.direction.angle_to(circle.normal) <= Leap.PI/2):
                        clockwiseness = "clockwise"
                    else:
                        clockwiseness = "counterclockwise"  


                if gesture.type == gesture.TYPE_SWIPE:
                    print "Swipe Swipe has been detected"

            time.sleep(0.1)


if __name__=="__main__":
    gesture = SendGestures()
