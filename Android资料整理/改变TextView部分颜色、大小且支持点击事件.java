int nottoLargeTextSize=(int) (getResources().getDimension(R.dimen.notTooLargeTextSize) /*/ AppConfig.sp*/);
			String telNum = getString(R.string.telNumber);
			ColorStateList blueColos = ColorStateList.valueOf(getResources().getColor(R.color.blue));
			SpannableStringBuilder spanBuilder = new SpannableStringBuilder(getString(R.string.bankTip)+" "+telNum);
			spanBuilder.setSpan(new ClickableSpan() {
									@Override
									public void onClick(View widget) {
										CommonUtil.call(getContext(), getString(R.string.telNumber));
									}

									@Override
									public void updateDrawState(TextPaint ds) {
										super.updateDrawState(ds);
										ds.setUnderlineText(false);
									}
								}, spanBuilder.length() - telNum.length(),
					spanBuilder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
			spanBuilder.setSpan(new TextAppearanceSpan(null, 0, nottoLargeTextSize, blueColos, null),
					spanBuilder.length() - telNum.length(),
					spanBuilder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
			tipTv.setText(spanBuilder);
			tipTv.setMovementMethod(LinkMovementMethod.getInstance());	//不设置无法点击