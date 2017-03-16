import tensorflow as tf
tf.logging.set_verbosity(tf.logging.DEBUG)
sess = tf.Session()

# import mnist data
from tensorflow.examples.tutorials.mnist import input_data
mnist = input_data.read_data_sets("MNIST_data/", one_hot=True,validation_size=5)

# restore the saved model
new_saver = tf.train.import_meta_graph('data/mnist_model/00000001/export.meta')
new_saver.restore(sess, 'data/mnist_model/00000001/export')



# print to see the restored variables
for v in tf.get_collection('variables'):
    print(v.name)
print(sess.run(tf.global_variables()))

# get saved weights
W = tf.get_collection('variables')[0]
b = tf.get_collection('variables')[1]

# placeholders for test images and labels
x = tf.placeholder(tf.float32, [None, 784],name='x')
y = tf.nn.softmax(tf.matmul(x, W) + b,name='y')
y_ = tf.placeholder(tf.float32, [None, 10],name='y_')

tf.add_to_collection('variables',W)
tf.add_to_collection('variables',b)

cross_entropy = tf.reduce_mean(tf.nn.softmax_cross_entropy_with_logits(logits=y, labels=y_))

train_step = tf.train.GradientDescentOptimizer(0.5).minimize(cross_entropy)

# save summaries for visualization
tf.summary.histogram('weights', W)
tf.summary.histogram('max_weight', tf.reduce_max(W))
tf.summary.histogram('bias', b)
tf.summary.scalar('cross_entropy', cross_entropy)
tf.summary.histogram('cross_hist', cross_entropy)

# merge all summaries into one op
merged=tf.summary.merge_all()

trainwriter=tf.summary.FileWriter('data/mnist_model'+'/logs/test',sess.graph)
init = tf.global_variables_initializer()
sess.run(init)

for i in range(1000):
    batch_xs, batch_ys = mnist.train.next_batch(2)
    summary, _ = sess.run([merged, train_step], feed_dict={x: batch_xs, y_: batch_ys})
    trainwriter.add_summary(summary, i)
# predict equation
y = tf.nn.softmax(tf.matmul(x, W) + b,name='y')

# compare predicted label and actual label
correct_prediction = tf.equal(tf.argmax(y,1), tf.argmax(y_,1))


accuracy = tf.reduce_mean(tf.cast(correct_prediction, tf.float32))

accu=sess.run(accuracy, feed_dict={x: mnist.test.images, y_: mnist.test.labels})
print(accu)
