//                    ObjectAnimator.ofFloat(btn, "translationY", 0, 300f).setDuration(1000).start();

//                    ObjectAnimator.ofPropertyValuesHolder(btn,
//                            PropertyValuesHolder.ofFloat("translationX", 0, 300f),
//                            PropertyValuesHolder.ofFloat("translationY", 0, 300f),
//                            PropertyValuesHolder.ofFloat("rotation", 0, 360f)).setDuration(1000).start();

                    ObjectAnimator anim1 = ObjectAnimator.ofFloat(btn, "translationY", 0, 600f);
                    ObjectAnimator anim2 = ObjectAnimator.ofFloat(btn, "translationX", 0, 600f);
                    ObjectAnimator anim3 = ObjectAnimator.ofFloat(btn, "rotation", 0, 360f);
//                    anim1.setInterpolator(new LinearInterpolator());
//                    anim1.setInterpolator(new AccelerateDecelerateInterpolator());
                    AnimatorSet set = new AnimatorSet();
                    set.play(anim1);
//                    set.play(anim1).with(anim2);
//                    set.play(anim3).after(anim1);
//                    set.playSequentially(anim1, anim2, anim3);    //顺序执行
//                    set.playTogether(anim1,anim2,anim3);  //同时执行
                    set.setDuration(1000);
                    set.start();