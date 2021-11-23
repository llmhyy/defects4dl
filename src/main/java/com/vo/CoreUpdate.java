package com.vo;


public class CoreUpdate {
    public final String[] edward46 = {"-        x = tf.cast(tf.squeeze(x), dtype=tf.float32)", "-        p = tf.cast(tf.squeeze(p), dtype=tf.float32)", "+        x = tf.cast(x, dtype=tf.float32)", "+        p = tf.cast(p, dtype=tf.float32)"};
    public final String[] keras11657 = {"-        return np.squeeze(self.model.predict(x, **kwargs))", "+        return np.squeeze(self.model.predict(x, **kwargs), axis=-1)"};
    public final String[] models1707 = {"-    return slim.l1_regularizer(scale=regularizer.l1_regularizer.weight)", "+    return slim.l1_regularizer(scale=float(regularizer.l1_regularizer.weight))", "-    return slim.l2_regularizer(scale=regularizer.l2_regularizer.weight)", "+    return slim.l2_regularizer(scale=float(regularizer.l2_regularizer.weight))"};
    public final String[] tensorflow_examples10 = {"-cost = -tf.reduce_sum(y*tf.log(activation)) # Cross entropy", "+cost = tf.reduce_mean(-tf.reduce_sum(y*tf.log(activation), reduction_indices=1)) # Cross entropy"};
    public final String[] deep_learning_tensorflow = {"-                cost = - tf.reduce_mean(ref_input * tf.log(model_output) +", "-                                             (1 - ref_input) * tf.log(1 - model_output))", "+                cost = - tf.reduce_mean(ref_input * tf.log(tf.clip_by_value(model_output, 1e-10, float('inf'))) +", "+                                        (1 - ref_input) * tf.log(tf.clip_by_value(1 - model_output, 1e-10, float('inf'))))"};
    public final String[] models166 = {"-cross_entropy = -tf.reduce_sum(y * tf.log(y_pred))", "+cross_entropy = tf.reduce_mean(", "+    tf.nn.softmax_cross_entropy_with_logits(y_logits, y))"};
    public final String[] keras2672 = {"+            denominator = K.maximum(denominator, 1e-15)"};
    public final String[] transformers1152 = {"-            all_toks = all_toks + (attr_value if isinstance(attr_value, (list, tuple)) else [attr_value])", "+            all_toks = all_toks + (list(attr_value) if isinstance(attr_value, (list, tuple)) else [attr_value])"};
    public final String[] transformers3711 = {"-        softmax_output = self.crit(pred_hid.view(-1, pred_hid.size(-1)), labels)", "+        softmax_output = self.crit(pred_hid, labels)"};
    public final String[] probability1010 = {"-    p_init = tfd.Categorical(probs=np.ones(nstates) / nstates)", "+    p_init = tfd.Categorical(probs=np.float32(np.ones(nstates) / nstates))", "-    pt = pswitch / (nstates - 1) * np.ones([nstates, nstates], dtype=np.float32)", "+    pt = pswitch / (nstates - 1) * np.ones([nstates, nstates])"};
    public final String[] tensorflow3168 = {"-    init_op = tf.initialize_all_variables()"};
    public final String[] keras1141 = {"-                K.set_value(self.states[i], states[i])", "+                K.set_value(self.states[i], K.eval(states[i]))"};
    public final String[] pytorch_lightning139 = {"+        self.val_check_batch = max(1, self.val_check_batch)"};
    public final String[] gpflow99 = {"+                storage['session'].run(tf.initialize_all_variables())"};
    public final String[] dcgan_tensorflow108 = {"-  return math.ceil(float(size) / float(stride))", "+  return int(math.ceil(float(size) / float(stride)))"};
    public final String[] transformers1962 = {"-    dynamic = shape_list(x)", "+    dynamic = tf.shape(x)"};
    public final String[] dcgan_tensorflow17 = {"-        self.d_loss_real = tf.nn.sigmoid_cross_entropy_with_logits(self.D_logits, tf.ones_like(self.D))", "-        self.d_loss_fake = tf.nn.sigmoid_cross_entropy_with_logits(self.D_logits_, tf.zeros_like(self.D_))", "-        self.g_loss = tf.nn.sigmoid_cross_entropy_with_logits(self.D_logits_, tf.ones_like(self.D_))", "+        self.d_loss_real = tf.reduce_mean(tf.nn.sigmoid_cross_entropy_with_logits(self.D_logits, tf.ones_like(self.D)))", "+        self.d_loss_fake = tf.reduce_mean(tf.nn.sigmoid_cross_entropy_with_logits(self.D_logits_, tf.zeros_like(self.D_)))", "+        self.g_loss = tf.reduce_mean(tf.nn.sigmoid_cross_entropy_with_logits(self.D_logits_, tf.ones_like(self.D_)))"};
    public final String[] ggnn_pytorch9 = {"-        test_loss += criterion(output, target).data[0]", "+        test_loss += criterion(output, target).data.item()"};
    public final String[] botorch162 = {"-            v = 0.5 + (1 - 1e-10) * (samples - 0.5)", "+            v = 0.5 + (1 - torch.finfo(samples.dtype).eps) * (samples - 0.5)"};
    public final String[] deep_learning_tensorflow0 = {"+                model_output = tf.nn.softmax(model_output)", "-            self.softmax_out = tf.nn.softmax(tf.matmul(last_layer, self.softmax_W) + self.softmax_b)", "+            self.softmax_out = tf.matmul(last_layer, self.softmax_W) + self.softmax_b"};
    public final String[] tutorials382 = {"-    crossEntropy = -torch.log(torch.gather(inp, 1, target.view(-1, 1)))", "+    crossEntropy = -torch.log(torch.gather(inp, 1, target.view(-1, 1)).squeeze(1))"};
    public final String[] allennlp4612 = {"-        orig_embeddings = span_embeddings_sum / span_embeddings_len", "+        orig_embeddings = span_embeddings_sum / torch.clamp_min(span_embeddings_len, 1)"};
    public final String[] segmentation_models_pytorch186 = {"-    tp = torch.sum(gt == pr)", "+    tp = torch.sum(gt == pr, dtype=pr.dtype)"};
    public final String[] deep_learning_tensorflow5 = {"-            self.softmax_out = tf.matmul(last_layer, self.softmax_W) + self.softmax_b", "+            self.softmax_out = tf.nn.softmax(tf.matmul(last_layer, self.softmax_W) + self.softmax_b)"};
    public final String[] dcgan_completion_tensorflow = {"+        self.saver = tf.train.Saver(max_to_keep=1)", "-                        max_to_keep=1)"};
    public final String[] models1063 = {"-  outputs = tf.split(axis=tf.nn.log_softmax(output), num_or_size_splits=beam_size, value=0)", "+  outputs = tf.split(axis=0, num_or_size_splits=beam_size, value=tf.nn.log_softmax(output))"};
    public final String[] models857 = {"-      logits, train_labels_node))", "+      labels=train_labels_node, logits=logits))"};
    public final String[] sact1 = {"-        tf.losses.softmax_cross_entropy(logits, one_hot_labels)", "+        tf.losses.softmax_cross_entropy(", "+            onehot_labels=one_hot_labels, logits=logits)"};
    public final String[] def = {};

    public  String[] getCoreFix(String bugId){
        switch (bugId){
            case "edward":
                return edward46;
            case "keras11657":
                return keras11657;
            case "models1707":
                return models1707;
            case "tensorflow-examples10":
                return tensorflow_examples10;
            case "deep-learning-tensorflow":
                return deep_learning_tensorflow;
            case "models166":
                return models166;
            case "keras2672":
                return keras2672;
            case "transformers1152":
                return transformers1152;
            case "transformers3711":
                return transformers3711;
            case "probability1010":
                return probability1010;
            case "tensorflow3168":
                return tensorflow3168;
            case "keras1141":
                return keras1141;
            case "pytorch-lightning139":
                return pytorch_lightning139;
            case "gpflow99":
                return gpflow99;
            case "dcgan-tensorflow108":
                return dcgan_tensorflow108;
            case "transformers1962":
                return transformers1962;
            case "dcgan-tensorflow17":
                return dcgan_tensorflow17;
            case "ggnn.pytorch9":
                return ggnn_pytorch9;
            case "botorch162":
                return botorch162;
            case "deep-learning-tensorflow0":
                return deep_learning_tensorflow0;
            case "tutorials382":
                return tutorials382;
            case "allennlp4612":
                return allennlp4612;
            case "segmentation_models.pytorch186":
                return segmentation_models_pytorch186;
            case "deep-learning-tensorflow5":
                return deep_learning_tensorflow5;
            case "dcgan-completion.tensorflow":
                return dcgan_completion_tensorflow;
            case "models1063":
                return models1063;
            case "models857":
                return models857;
            case "sact1":
                return sact1;
            default:
                return def;
        }
    }
}


