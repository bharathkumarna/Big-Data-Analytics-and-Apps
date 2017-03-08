

from __future__ import print_function

import tensorflow as tf
import numpy
import matplotlib.pyplot as plt
rng = numpy.random

learning_rate = 0.01
training_epochs = 1000
display_step = 50



train_X = numpy.asarray([2.31,7.07,7.07,2.18,2.18,2.18,7.87,7.87,7.87,7.87,7.87,7.87,7.87,8.14,8.14,8.14,8.14,8.14,8.14,8.14,8.14,8.14,
8.14,8.14,8.14,8.14,8.14,8.14,8.14,8.14,8.14,8.14,8.14,8.14,8.14,5.96,5.96,5.96,5.96,2.95,2.95,6.91,6.91,6.91,6.91,6.91,6.91,6.91,6.91,
6.91,5.64,5.64,5.64,5.64,4,1.22,0.74,1.32,5.13,5.13,5.13,5.13,5.13,5.13,1.38,3.37,3.37,6.07,6.07,6.07,10.81,10.81,10.81,10.81,12.83,12.83,
12.83,12.83,12.83,12.83,4.86,4.86,4.86,4.86,4.49,4.49,4.49,4.49,3.41,3.41,3.41,3.41,15.04,15.04,15.04,2.89,2.89,2.89,2.89,2.89])
train_Y = numpy.asarray([65.2,78.9,61.1,45.8,54.2,58.7,66.6,96.1,100,85.9,94.3,82.9,39,61.8,84.5,56.5,29.3,81.7,36.6,69.5,98.1,89.2,91.7,100,
94.1,85.7,90.3,88.8,94.4,87.3,94.1,100,82,95,96.9,68.2,61.4,41.5,30.2,21.8,15.8,2.9,6.6,6.5,40,33.8,33.3,85.5,95.3,62,45.7,63,21.1,21.4,47.6,
21.9,35.7,40.5,29.2,47.2,66.2,93.4,67.8,43.4,59.5,17.8,31.1,21.4,36.8,33,6.6,17.5,7.8,6.2,6,45,74.5,45.8,53.7,36.6,33.5,70.4,32.2,46.7,48,56.1,
45.1,56.8,86.3,63.1,66.1,73.9,53.6,28.9,77.3,57.8,69.6,76,36.9,62.5])
n_samples = train_X.shape[0]

X = tf.placeholder("float")
Y = tf.placeholder("float")

W = tf.Variable(rng.randn(), name="weight")
b = tf.Variable(rng.randn(), name="bias")

pred = tf.add(tf.multiply(X, W), b)

cost = tf.reduce_sum(tf.pow(pred-Y, 2))/(2*n_samples)
optimizer = tf.train.GradientDescentOptimizer(learning_rate).minimize(cost)

init = tf.global_variables_initializer()

with tf.Session() as sess:
    sess.run(init)

    for epoch in range(training_epochs):
        for (x, y) in zip(train_X, train_Y):
            sess.run(optimizer, feed_dict={X: x, Y: y})


        if (epoch+1) % display_step == 0:
            c = sess.run(cost, feed_dict={X: train_X, Y:train_Y})
            print("Epoch:", '%04d' % (epoch+1), "cost=", "{:.9f}".format(c), \
                "W=", sess.run(W), "b=", sess.run(b))

    print("Optimization Finished!")
    training_cost = sess.run(cost, feed_dict={X: train_X, Y: train_Y})
    print("Training cost=", training_cost, "W=", sess.run(W), "b=", sess.run(b), '\n')


    plt.plot(train_X, train_Y, 'ro', label='Original data')
    plt.plot(train_X, sess.run(W) * train_X + sess.run(b), label='Fitted line')
    plt.legend()
    plt.show()


    test_X = numpy.asarray([8.56,8.56,8.56,8.56,8.56,8.56,8.56,8.56,8.56,8.56,8.56,10.01,10.01,10.01,10.01,10.01,10.01,10.01,10.01,10.01])
    test_Y = numpy.asarray([79.9,71.3,85.4,87.4,90,96.7,91.9,85.2,97.1,91.2,54.4,81.6,92.9,95.4,84.2,88.2,72.5,82.6,73.1,65.2])

    print("Testing... (Mean square loss Comparison)")
    testing_cost = sess.run(
        tf.reduce_sum(tf.pow(pred - Y, 2)) / (2 * test_X.shape[0]),
        feed_dict={X: test_X, Y: test_Y}) 
    print("Testing cost=", testing_cost)
    print("Absolute mean square loss difference:", abs(
        training_cost - testing_cost))

    plt.plot(test_X, test_Y, 'bo', label='Testing data')
    plt.plot(train_X, sess.run(W) * train_X + sess.run(b), label='Fitted line')
    plt.legend()
    plt.show()
